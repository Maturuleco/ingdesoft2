/*
 * 
 * Esta Clase se ocupa de la transmición de Mensajes,
 * cuando le llega un Mensaje por su cola de Entrada
 * se ocupa de transmitirlo y cuando se asegura de que llegó
 * manda el mismo mensaje por la cola de salida.
 * 
 */
package DataSender;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mensaje;
import model.MensajeGeneral;
import red_gsm.MensajeGSM;

/**
 *
 * @author Tas
 * 
 */
public class DataSender extends ComunicacionSobreGSM.MessajeSender implements Runnable {

    private BlockingQueue<MensajeGSM> modemSalida;
    private BlockingQueue<MensajeGSM> modemEntrada;
    private BlockingQueue<? super Mensaje> salida;
    private BlockingQueue<Mensaje> entrada;
    private static final long sleepTime = 1000;

    public void setModemEntrada(BlockingQueue<MensajeGSM> modemEntrada) {
        this.modemEntrada = modemEntrada;
    }

    public void setModemSalida(BlockingQueue<MensajeGSM> modemSalida) {
        this.modemSalida = modemSalida;
    }

    public void setEntrada(BlockingQueue<Mensaje> entrada) {
        this.entrada = entrada;
    }

    public void setSalida(BlockingQueue<? super Mensaje> salida) {
        this.salida = salida;
    }

    public DataSender() {
        destino = terminalremotafinal.Main.estacionCentral;
    }

    
    @Override
    public void run() {
        while (true) {
            if (msjEnProceso == 0) {
                if (!sensarEntradaDatos()) {
                    try {
                        // Duermo un segundo
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException ex) {
                    }
                }
            }
            while (msjEnProceso != 0) {
                sensarEntradaModem();
                try {
                    // Duermo 1/2 segundo
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
            }
            // Esto está para ir leyendo los posibles mensajes con delay
            // que me lleguen de comunicaciones pasadas
            sensarEntradaModem();
        }
    }

    private boolean sensarEntradaDatos() {
        Mensaje cabeza = entrada.poll();
        if (cabeza != null) {
            send(cabeza);
            return true;
        }
        return false;
    }

    private void sensarEntradaModem() {
        MensajeGSM respuesta = modemEntrada.poll();
        if (respuesta != null) {
            recive(respuesta);
        }
    }

    protected Boolean checkMsj(String[] cuerpo) {
        if (cuerpo.length != 4)
            return false;
        Long timeStamp = Long.valueOf(cuerpo[2]);
        // Checkeo de que el ACK sea un ACK y que no sea de un mensaje viejo
        return (timeStampMsjActual == timeStamp && cuerpo[0].equals("ACK"));
    }

    @Override
    protected synchronized Boolean phisicalSend(MensajeGSM msj) {
        try {
            modemSalida.put(msj);   
        } catch (InterruptedException ex) {
            Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    protected void transmitionFinished(MensajeGeneral mensaje) {
        try {
            // TODO: Mejorar este casteo !!!
            if (mensaje.getClass() == Mensaje.class) {
                Mensaje msj = (Mensaje) mensaje;
                salida.put(msj);
            } else {
                System.out.println("\n\n-----------A DATA SENDER LE LLEGA UN MENSAJE QUE NO ES DE TIPO MENSAJE----------\n\n");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}