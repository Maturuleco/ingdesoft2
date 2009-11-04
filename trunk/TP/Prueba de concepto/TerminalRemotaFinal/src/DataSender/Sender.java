/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataSender;


import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import red_gsm.MensajeGSM;
import terminalremotafinal.Main;

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
                System.out.println("Se manda al Modem un mensaje");
                //Espero y reintento
                sleep(tiempoEspera);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
      //  System.out.println("Sender Stopped");
    }
    
    public void requestStop(){
        keepTrying = false;
        //System.out.println("Sender Request Stop");
    }

    public void setModem(BlockingQueue<MensajeGSM> modem) {
        this.modem = modem;
    }

}
