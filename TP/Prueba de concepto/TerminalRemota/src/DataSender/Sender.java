/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataSender;

import TR.Main;
import java.util.concurrent.BlockingQueue;
import red_gsm.MensajeGSM;

/**
 *
 * @author tas
 */
public class Sender extends Thread {
    
    private static final long tiempoEspera = 15000;
    
    private volatile boolean keepTrying = true;
    private MensajeGSM mensaje;
    private BlockingQueue<MensajeGSM> modem;
    
    public Sender(String msj, BlockingQueue<MensajeGSM> m) {
        mensaje = new MensajeGSM(0, Main.estacionCentral, msj);
        modem = m;
    }
    
    public Sender(String msj) {
        mensaje = new MensajeGSM(0, Main.estacionCentral, msj);
    }
    
    @Override
    public void run() {
        while (keepTrying) {
            try {
                modem.put(mensaje);
                //Espero y reintento
                sleep(tiempoEspera);
            } catch (InterruptedException ex) { }
        }
    }
    
    public void requestStop(){
        keepTrying = false;
    }

    public void setModem(BlockingQueue<MensajeGSM> modem) {
        this.modem = modem;
    }

}
