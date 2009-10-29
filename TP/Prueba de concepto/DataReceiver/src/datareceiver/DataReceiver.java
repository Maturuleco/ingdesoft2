/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datareceiver;

import java.util.concurrent.BlockingQueue;
import model.DatoAlmacenado;
import model.DatoSensado;
import model.Mensaje;
import networkcontroller.HeartbeatMessege;

/**
 *
 * @author mar
 */
public class DataReceiver extends Thread{
    private BlockingQueue<Mensaje> entrada;
    private BlockingQueue<DatoAlmacenado> salidaValidator;
    private BlockingQueue<HeartbeatMessege> salidaNetworkController;
    private static final long sleepTime = 100;

    public DataReceiver () {
    }

    public void setEntrada(BlockingQueue<Mensaje> entrada) {
        this.entrada = entrada;
    }

    public void setSalidaValidator(BlockingQueue<DatoAlmacenado> salidaValidator) {
        this.salidaValidator = salidaValidator;
    }

    public void setSalidaNetworkController(BlockingQueue<HeartbeatMessege> salidaNetworkController) {
        this.salidaNetworkController = salidaNetworkController;
    }

    @Override
    public void run() {

        while (true) {
            if (! sensarEntradaDatos() ) {
                try {
                    // Duermo un segundo
                    sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }

    private boolean sensarEntradaDatos() {
        Mensaje cabeza = entrada.poll();
        if (cabeza != null) {
            for (DatoSensado dato : cabeza.getDatos()){
                DatoAlmacenado datoAlm = new DatoAlmacenado(dato.getIdSensor(),
                        dato.getTimeStamp(),dato.getFactor(),dato.getValor(),
                        cabeza.getIdTR(),cabeza.getDataSource());
                enviarValidator(datoAlm);
                HeartbeatMessege heartbeat = new HeartbeatMessege(cabeza.getIdTR(),dato.getTimeStamp(), null, null);
                enviarNetworkController(heartbeat);
            }
            return true;
        }
        return false;
    }

    public void enviarValidator(DatoAlmacenado m) {
        salidaValidator.add(m);
    }

    public void enviarNetworkController(HeartbeatMessege m) {
        salidaNetworkController.add(m);
    }

}
