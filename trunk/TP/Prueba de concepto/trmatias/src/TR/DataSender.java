/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TR;

import red_gsm.MensajeGSM;
import model.Mensaje;
import red_gsm.MensajeModemGSM;
import threadcomunication.ColaSync;

/**
 *
 * @author Administrador
 */

public class DataSender extends Thread {
    private static final int estacionCentral = 0;
    private static final int tamañoBuffer = 10;
    private int estadoBuffer = 0;
    private Sender[] enviando;
    private Mensaje[] mensajesPendientes;
    private ColaSync<Mensaje> salida;
    private ColaSync<Mensaje> entrada;
    private ColaSync<MensajeModemGSM> modemSalida;
    private ColaSync<MensajeModemGSM> modemEntrada;
    
    public DataSender(ColaSync<Mensaje> e, ColaSync<Mensaje> s, ColaSync<MensajeModemGSM> m) {
        enviando = new Sender[tamañoBuffer];
        salida = s;
        entrada = e;
        modemSalida = m;
    }
    
    public void run() {
        
    }

    private void send(Mensaje m, int id)
    {
        String mensaje = m.toString();
        // Le pongo un Id al Mensaje en su cuerpo
        mensaje = id+"#"+mensaje;
        Sender s = new Sender(mensaje, modemSalida);
        enviando[id] = s;
        mensajesPendientes[id] = m;
        estadoBuffer++;
        s.start();
    }

    private Boolean recive(MensajeGSM msj) {
        if (msj.getOrigen() != estacionCentral)
            return false;
        else
        {
            String contenido = new String(msj.getMensaje());
            //Saco el idMsj
            String[] cuerpo = contenido.split("#");
            if ( cuerpo[0].equals("CONFIG") )
                manejarMensajeConfiguracion(msj);
            else if ( cuerpo[0].equals("ACK") ) {
                int id = Integer.valueOf(cuerpo[1]);
                enviando[id].requestStop();
                enviando[id] = null;
                salida.put(mensajesPendientes[id]);
                estadoBuffer--;
            }
            else
                return false;
        }
        return true;
    }

    private void manejarMensajeConfiguracion(MensajeGSM msj) {}
    
}
