/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ComunicacionSobreGSM;

import java.text.ParseException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MensajeGeneral;
import Herramientas.ValidatingTools;
import red_gsm.MensajeGSM;

/**
 *
 * @author tas
 */
public abstract class MessageReciever<TipoMensaje extends MensajeGeneral> {

    protected Dictionary<Integer, Long> timeStamps;

    public MessageReciever() {
        this.timeStamps = new Hashtable<Integer, Long>();
    }
    
    protected abstract TipoMensaje parseMensaje(String str) throws ParseException;
    protected abstract void phisicalReply(MensajeGSM msj);
    protected abstract void procesarMensaje(TipoMensaje msj);

    protected void receive(MensajeGSM msj) {
        String contenido = new String(msj.getMensaje());
        //System.out.println("RECEPCION\tEl message receiver recibe un mensaje");

        String[] cuerpo = contenido.split("#");
        // frag#num#msj#firma
        // Esto ya lo validé antes, pero está buno re-hacerlo.. capaz
        if ( cuerpo[0].equals("M") || cuerpo[0].equals("L")) {
            try {
                // Cuerpo 0 y 1 son para fragmentacion que en esta etapa
                // no se implementa, recivo solo mensajes enteros.
                TipoMensaje mensaje = parseMensaje(cuerpo[2]);
                Boolean nuevo = checkMsj(mensaje);
                sendACK(msj);
                if (nuevo) {
                    procesarMensaje(mensaje);
//                    System.out.println("El Message receiver manda al data receiver "+mensaje.toString());
                }
            } catch (ParseException ex) {
                Logger.getLogger(MessageReciever.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Messege Receiver rebota por desconocido: "+contenido);
        }

    }

    private void sendACK(MensajeGSM m) {
        try {
            Integer destino = m.getOrigen();
            String[] partes = m.getMensaje().split("#");
            Integer num = Integer.valueOf(partes[1]);
            TipoMensaje mensaje;

            mensaje = parseMensaje(partes[2]);
            long timeStamp = mensaje.getTimeStamp();
            String respuesta = "ACK#" + num + "#" + timeStamp;
            String hash = ValidatingTools.getHash(respuesta);
            MensajeGSM ack = new MensajeGSM(0, destino, respuesta + "#" + hash, 0);
            
            phisicalReply(ack);
        } catch (ParseException ex) {
            System.out.println("El Mensaje no se pudo parsear, formato incorrecto");
            Logger.getLogger(MessageReciever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected Boolean checkMsj(TipoMensaje mensaje) {
        Integer id = mensaje.getIdTR();

        if ( timeStamps.get(id) == null) {
            timeStamps.put(id, mensaje.getTimeStamp());
            return true;
        } else {
            long timeStampMsj = mensaje.getTimeStamp();
            if (timeStamps.get(id) >= timeStampMsj)
                return false;
            else {
                timeStamps.put(id, mensaje.getTimeStamp());
                return true;
            }
        }

    }
    
}
