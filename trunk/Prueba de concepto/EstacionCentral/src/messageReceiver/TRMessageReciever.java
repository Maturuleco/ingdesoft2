/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package messageReceiver;

import java.text.ParseException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mensaje;
import red_gsm.MensajeGSM;

/**
 *
 * @author matiaz
 */
public class TRMessageReciever extends ComunicacionSobreGSM.MessageReciever<Mensaje> implements Runnable {
    
    private BlockingQueue<MensajeGSM> modemSalida;
    private BlockingQueue<MensajeGSM> modemEntrada;
    private BlockingQueue<Mensaje> salidaDatos;
    private static final long sleepTime = 100;
    
    public void setModemEntrada(BlockingQueue<MensajeGSM> modemEntrada) {
        this.modemEntrada = modemEntrada;
    }

    public void setModemSalida(BlockingQueue<MensajeGSM> modemSalida) {
        this.modemSalida = modemSalida;
    }

    public void setSalida(BlockingQueue<Mensaje> salida) {
        this.salidaDatos = salida;
    }

    @Override
    public void run() {
        while (true) {
            try {
                MensajeGSM mensaje = modemEntrada.take();
                receive(mensaje);
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(TRMessageReciever.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected Mensaje parseMensaje(String str) throws ParseException {
        return Mensaje.parse(str);
    }

    @Override
    protected void phisicalReply(MensajeGSM msj) {
        try {
            modemSalida.put(msj);
        } catch (InterruptedException ex) {
            Logger.getLogger(TRMessageReciever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void procesarMensaje(Mensaje msj) {
        try {
            salidaDatos.put(msj);
        } catch (InterruptedException ex) {
            Logger.getLogger(TRMessageReciever.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
