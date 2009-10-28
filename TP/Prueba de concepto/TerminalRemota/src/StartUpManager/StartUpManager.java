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
        String mensaje = "Raise" + "#" + Initialize.idTR + "#" + d.getTime();
        mensaje = mensaje + "#" + ValidatingTools.getHash(mensaje);
        MensajeGSM mensajeInicial = 
                new MensajeGSM(0, Initialize.estacionCentral, mensaje, priority);
        try {
            System.out.println("StartUp manda\n");
            salida.put(mensajeInicial);
            System.out.println("Si, manda\n");
        } catch (InterruptedException ex) { }
    }


}
