/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StartUpManager;

import TR.Initialize;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import model.ValidatingTools;
import red_gsm.MensajeToModemGSM;

/**
 *
 * @author matiaz
 */
public class StartUpManager extends Thread {
    private static final int priority = 10;
    private BlockingQueue<MensajeToModemGSM> salida;

    public void setSalida(BlockingQueue<MensajeToModemGSM> salida) {
        this.salida = salida;
    }

    @Override
    public void run() {
        Date d = new Date();
        String mensaje = Initialize.TRid + "#" + d.getTime();
        mensaje = mensaje + "#" + ValidatingTools.getHash(mensaje);
        MensajeToModemGSM mensajeInicial = 
                new MensajeToModemGSM(Initialize.estacionCentral, mensaje, priority);
        try {
            salida.put(mensajeInicial);
        } catch (InterruptedException ex) { }
    }


}
