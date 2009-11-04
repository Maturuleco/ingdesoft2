/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package messageReceiver;

import java.text.ParseException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mensaje;
import model.ValidatingTools;
import red_gsm.MensajeGSM;

/**
 *
 * @author matiaz
 */
public class TRMessageReciever extends Thread {
    private BlockingQueue<MensajeGSM> modemSalida;
    private BlockingQueue<MensajeGSM> modemEntrada;
    private BlockingQueue<Mensaje> salidaDatos;
    private static final long sleepTime = 100;
    private Dictionary<Integer, Long> timeStamps;

    public void setModemEntrada(BlockingQueue<MensajeGSM> modemEntrada) {
        this.modemEntrada = modemEntrada;
    }

    public void setModemSalida(BlockingQueue<MensajeGSM> modemSalida) {
        this.modemSalida = modemSalida;
    }

    public void setSalida(BlockingQueue<Mensaje> salida) {
        this.salidaDatos = salida;
    }

    public TRMessageReciever() {
        this.timeStamps = new Hashtable<Integer, Long>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                MensajeGSM mensaje = modemEntrada.take();
                receive(mensaje);
                sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(TRMessageReciever.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void receive(MensajeGSM msj) {
        String contenido = new String(msj.getMensaje());
        System.out.println("RECEPCION\tEl message receiver recibe un mensaje");

        String[] cuerpo = contenido.split("#");
        // frag#num#msj#firma
        // Esto ya lo validé antes, pero está buno re-hacerlo.. capaz
        if ( cuerpo[0].equals("M") || cuerpo[0].equals("L")) {
            try {
                // Cuerpo 0 y 1 son para fragmentacion que en esta etapa
                // no se implementa, recivo solo mensajes enteros.
                Mensaje mensaje = Mensaje.parse(cuerpo[2]);
                Boolean nuevo = checkMsj(mensaje);
                sendACK(msj);
                if (nuevo) {
                    try {
                        salidaDatos.put(mensaje);
//                        System.out.println("El Message receiver manda al data receiver "+mensaje.toString());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TRMessageReciever.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(TRMessageReciever.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("El Messege Receiver rebota por desconocido: "+contenido);
        }

    }

    private Boolean checkMsj(Mensaje mensaje) {
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
    
    private void sendACK(MensajeGSM m) {
        Integer destino = m.getOrigen();
        String[] partes = m.getMensaje().split("#");
        Integer num = Integer.valueOf(partes[1]);
        Mensaje mensaje;
        try {
            mensaje = Mensaje.parse(partes[2]);
            long timeStamp = mensaje.getTimeStamp();
            String respuesta = "ACK#"+num+"#"+timeStamp;
            String hash = ValidatingTools.getHash(respuesta);
            MensajeGSM ack = new MensajeGSM(0, destino, respuesta+"#"+hash, 0);
            System.out.println("ACK\tEl Message receiver manda el ACK: "+ack);
            modemSalida.put(ack);

        } catch (InterruptedException ex) {
            Logger.getLogger(TRMessageReciever.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TRMessageReciever.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

}
