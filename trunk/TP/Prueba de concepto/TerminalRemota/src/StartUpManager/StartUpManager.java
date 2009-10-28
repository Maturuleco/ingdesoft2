/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StartUpManager;

import TR.Initialize;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import model.ValidatingTools;
import red_gsm.MensajeGSM;

/**
 *
 * @author matiaz
 */
public class StartUpManager extends Thread {
    private static final int priority = 3;
    private BlockingQueue<MensajeGSM> salida;

    public void setSalida(BlockingQueue<MensajeGSM> salida) {
        this.salida = salida;
    }

    @Override
    public void run() {
        Date d = new Date();
        String mensaje = Initialize.TRid + "#" + d.getTime();
        mensaje = mensaje + "#" + ValidatingTools.getHash(mensaje);
        MensajeGSM mensajeInicial = 
                new MensajeGSM(0, Initialize.estacionCentral, mensaje, priority);
        try {
            salida.put(mensajeInicial);
        } catch (InterruptedException ex) { }
    }


}
