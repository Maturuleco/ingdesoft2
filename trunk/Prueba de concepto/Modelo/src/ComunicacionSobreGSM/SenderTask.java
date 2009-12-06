/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ComunicacionSobreGSM;

import red_gsm.MensajeGSM;

import java.util.TimerTask;

/**
 *
 * @author tas
 */
public class SenderTask extends TimerTask {
    
    private MensajeGSM mensaje;
    private MessajeSender handler = null;

    public SenderTask(String msj, MessajeSender handler) {
        mensaje = new MensajeGSM(0, handler.getDestino(), msj);
        this.handler = handler;
    }
    
    @Override
    public void run() {
        handler.phisicalSend(mensaje);
    }
}
