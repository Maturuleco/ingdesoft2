/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TR;

import java.util.concurrent.BlockingQueue;
import red_gsm.MensajeModemGSM;
import threadcomunication.ColaSync;

/**
 *
 * @author tas
 */
public class Sender extends Thread {
    private static final int estacionCentral = 0;
    private static final long tiempoEspera = 10000;
    
    private volatile boolean keepTrying = true;
    private MensajeModemGSM mensaje;
    private BlockingQueue<MensajeModemGSM> modem;
    
    public Sender(String msj, BlockingQueue<MensajeModemGSM> m) {
        mensaje = new MensajeModemGSM(estacionCentral, msj);
        modem = m;
    }
    
    public Sender(String msj) {
        mensaje = new MensajeModemGSM(estacionCentral, msj);
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

    public void setModem(BlockingQueue<MensajeModemGSM> modem) {
        this.modem = modem;
    }

}
