/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StartUpManager;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import model.ValidatingTools;
import red_gsm.MensajeGSM;
import terminalremotafinal.Main;

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
        String mensaje = "Raise" + "#" + Main.idTR + "#" + d.getTime();
        mensaje = mensaje + "#" + ValidatingTools.getHash(mensaje);
        MensajeGSM mensajeInicial = 
                new MensajeGSM(0, Main.estacionCentral, mensaje, priority);
        try {
            salida.put(mensajeInicial);
            System.out.println("Se manda "+mensajeInicial);
        } catch (InterruptedException ex) { }
    }


}
