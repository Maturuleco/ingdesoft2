/**
 * Created by IntelliJ IDEA.
 * User: Mar
 * Date: 25/10/2009
 * Time: 14:53:08
 * To change this template use File | Settings | File Templates.
 */

package DataManager;

import java.util.concurrent.BlockingQueue;
import model.Mensaje;


public class DataManager extends Thread{

    private BlockingQueue<Mensaje> salida;
    private BlockingQueue<Mensaje> entrada;
    private static final long sleepTime = 10000;

    private StringBuffer dataBuffer = new StringBuffer(100000);
    private int msjEnProceso = 0;

    public DataManager () {
    }

    public void setEntrada(BlockingQueue<Mensaje> entrada) {
        this.entrada = entrada;
    }

    public void setSalida(BlockingQueue<Mensaje> salida) {
        this.salida = salida;
    }

    @Override
    public void run() {
        while (true) {
            if (msjEnProceso == 0) {
                if (! sensarEntradaDatos() ) {
                    try {
                        // Duermo un segundo
                        sleep(sleepTime);
                    } catch (InterruptedException ex) {}
                }
            }
        }
    }

    private boolean sensarEntradaDatos() {
        Mensaje cabeza = entrada.poll();
        if (cabeza != null) {
            guardar(cabeza);
            return true;
        }
        return false;
    }

    private void guardar(Mensaje m){
       dataBuffer.append(m);
    }

}
