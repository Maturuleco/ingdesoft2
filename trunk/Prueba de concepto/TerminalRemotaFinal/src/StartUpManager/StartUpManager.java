/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package StartUpManager;

import Comunication.MessajeSender;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MensajeGeneral;
import model.MensajeRaise;
import red_gsm.MensajeGSM;
import terminalremotafinal.Main;

/**
 *
 * @author matiaz
 */
public class StartUpManager extends MessajeSender implements Runnable {
    private static final int priority = 3;
    private BlockingQueue<MensajeGSM> modemSalida;
    private BlockingQueue<MensajeGSM> modemEntrada;

    public void setSalida(BlockingQueue<MensajeGSM> salida) {
        this.modemSalida = salida;
    }
    
    public void setEntrada(BlockingQueue<MensajeGSM> entrada) {
        this.modemEntrada = entrada;
    }

    @Override
    public void run() {
        MensajeRaise mensaje = new MensajeRaise(Main.idTR, Main.latitud, Main.longitud);
        send(mensaje);
    }

    @Override
    protected Boolean checkMsj(String[] cuerpo) {
        if (cuerpo.length != 5)
            return false;
        if (Main.idTR != Integer.valueOf(cuerpo[1]))
            return false;
        if (!cuerpo[0].equalsIgnoreCase("ACKUP"))
            return false;
        Long timeStamp = Long.valueOf(cuerpo[2]);
        return timeStampMsjActual == timeStamp;
    }

    @Override
    protected Boolean phisicalSend(MensajeGSM msj) {
        try {
            modemSalida.put(msj);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(StartUpManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    protected void transmitionFinished(MensajeGeneral msj) { }

}
