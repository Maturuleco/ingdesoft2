
package validator;

import com.db4o.ObjectServer;
import java.util.concurrent.BlockingQueue;
import Datos.DatoAlmacenado;

/**
 *
 * @author Santiago Avendaño
 */
public class ValidatorManager implements Runnable{

    private static final long tiempoEspera = 100;
    private volatile boolean keepTrying = true;

    private ValidatorDAO outlierDAO = null;
    private Validador validador = new Validador();
    private BlockingQueue<DatoAlmacenado> entradaDatosAValidar;
    private BlockingQueue<DatoAlmacenado> salidaDatosAAlmacenar;

    public ValidatorManager(ObjectServer server) {
        outlierDAO = new ValidatorDAO(server);
    }

    public void setEntradaDatos(BlockingQueue<DatoAlmacenado> entradaDatos) {
        this.entradaDatosAValidar = entradaDatos;
    }
    
    public void setSalidaDatos(BlockingQueue<DatoAlmacenado> salidaDatos) {
        this.salidaDatosAAlmacenar = salidaDatos;
    }

    @Override
    public void run() {
        while (keepTrying) {
            try {
                DatoAlmacenado dato = entradaDatosAValidar.poll();
                if (dato != null) {
                    if (validador.validar(dato)){
                        salidaDatosAAlmacenar.put(dato);
                    } else {
                        outlierDAO.escribirDatosOutlier(dato);
                    }
                    Thread.sleep(tiempoEspera);
                }
            } catch (InterruptedException ex) { }
        }
    }

}
