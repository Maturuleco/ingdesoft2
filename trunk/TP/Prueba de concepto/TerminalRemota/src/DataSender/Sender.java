/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataSender;

import TR.Initialize;
import java.util.concurrent.BlockingQueue;
import red_gsm.MensajeToModemGSM;

/**
 *
 * @author tas
 */
public class Sender extends Thread {
    
    private static final long tiempoEspera = 10000;
    
    private volatile boolean keepTrying = true;
    private MensajeToModemGSM mensaje;
    private BlockingQueue<MensajeToModemGSM> modem;
    
    public Sender(String msj, BlockingQueue<MensajeToModemGSM> m) {
        mensaje = new MensajeToModemGSM(Initialize.estacionCentral, msj);
        modem = m;
    }
    
    public Sender(String msj) {
        mensaje = new MensajeToModemGSM(Initialize.estacionCentral, msj);
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

    public void setModem(BlockingQueue<MensajeToModemGSM> modem) {
        this.modem = modem;
    }

}
