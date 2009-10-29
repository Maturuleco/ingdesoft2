/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datareceiver;

import java.util.concurrent.BlockingQueue;
import model.DatoAlmacenado;
import model.DatoSensado;
import model.Mensaje;

/**
 *
 * @author mar
 */
public class DataReceiver extends Thread{
    private BlockingQueue<Mensaje> entrada;
    private BlockingQueue<DatoAlmacenado> salida;
    private static final long sleepTime = 100;

    public DataReceiver () {
    }

    public void setEntrada(BlockingQueue<Mensaje> entrada) {
        this.entrada = entrada;
    }

    public void setSalida(BlockingQueue<DatoAlmacenado> salida) {
        this.salida = salida;
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
                enviar(datoAlm);
            }
            return true;
        }
        return false;
    }

    public void enviar(DatoAlmacenado m) {
        salida.add(m);
    }

}
