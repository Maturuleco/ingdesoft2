/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Comunication;

import java.util.Date;
import java.util.Timer;
import model.MensajeGeneral;
import model.ValidatingTools;
import red_gsm.MensajeGSM;

/**
 *
 * @author tas
 */
public abstract class MessajeSender {
    
    protected static final long tiempoEsperaRetransmición = 15000;
    protected MensajeGeneral mensajeActual;
    protected int msjEnProceso = 0;
    protected long timeStampMsjActual;
    protected SenderTask[] enviando;
    protected Timer timer = new Timer();

    protected abstract Boolean checkMsj(String[] cuerpo);
    protected abstract Boolean phisicalSend(MensajeGSM msj);
    protected abstract void transmitionFinished(MensajeGeneral msj);
    
    protected void formatPartes(String[] partes) {
        for (int i = 0; i < partes.length - 1; i++) {
            String msj = partes[i];
            // Le pongo un Id al Mensaje en su cuerpo
            // Tb le pongo una M (more) para decir que
            // no es la última fracción
            msj = "M#" + i + "#" + msj;
            partes[i] = msj;
        }
        // Hago uno para el último
        int i = partes.length - 1;
        String msj = partes[i];
        // L de Last
        msj = "L#" + i + "#" + msj;
        partes[i] = msj;
    }

    protected String[] fraccionar(String msj) {
        // TODO: los msj SMS tienen 160 caracteres!
        String[] partes = new String[1];
        partes[0] = msj;
        return partes;
    }

    protected void firmar(String[] mensajes) {
        for (int i = 0; i < mensajes.length; i++) {
            String hash = ValidatingTools.getHash(mensajes[i]);
            mensajes[i] += "#" + hash;
        }
    }

    protected void send(MensajeGeneral m) {
        mensajeActual = m;
        timeStampMsjActual = m.getTimeStamp();
        String mensaje = m.toString();
        String[] partes = fraccionar(mensaje);

        formatPartes(partes);
        firmar(partes);

        enviando = new SenderTask[partes.length];
        for (int i = 0; i < partes.length; i++) {
            SenderTask s = new SenderTask(partes[i], this);
            enviando[i] = s;
            msjEnProceso++;
            timer.scheduleAtFixedRate(s, new Date(), tiempoEsperaRetransmición);
        }
    }

    protected Boolean recive(MensajeGSM msj) {
        String contenido = new String(msj.getMensaje());
        String[] cuerpo = contenido.split("#");
        // Esto ya lo validé antes, pero está buno re-hacerlo.. capaz
        if (checkMsj(cuerpo)) {
            int id = Integer.valueOf(cuerpo[1]);
            System.out.println("Se recive: " + contenido);
            if (enviando[id] != null) {
                enviando[id].cancel();
                enviando[id] = null;
                msjEnProceso--;
            }
            // Cuando lo termino de transmitir se lo devuelvo
            if (msjEnProceso == 0) {
                    transmitionFinished(mensajeActual);
            }
            return true;
        } else {
            System.out.println("Se rebota respuesta por deconocida o antiguedad: " + contenido);
        }
        return false;
    }

}
