/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package validator;

import com.db4o.ObjectServer;
import java.util.concurrent.BlockingQueue;
import model.DatoAlmacenado;

/**
 *
 * @author Santiago Avendaño
 */
public class ValidatorManager implements Runnable{

    private static final long tiempoEspera = 100;
    private volatile boolean keepTrying = true;

    private ValidatorDAO validadorDAO = null;
    private Validador validador = new Validador();
    private BlockingQueue<DatoAlmacenado> entradaDatos;

    public ValidatorManager(ObjectServer server) {
        validadorDAO = new ValidatorDAO(server);
    }

    public void setEntradaDatos(BlockingQueue<DatoAlmacenado> entradaDatos) {
        this.entradaDatos = entradaDatos;
    }
    
    @Override
    public void run() {
        while (keepTrying) {
            try {
                DatoAlmacenado dato = entradaDatos.poll();
                if (dato != null) {
                    if (validador.validar(dato)){
                        validadorDAO.escribirDatosValidos(dato);
                    } else {
                        validadorDAO.escribirDatosOutlier(dato);
                    }
                    Thread.sleep(tiempoEspera);
                }
            } catch (InterruptedException ex) { }
        }
    }

}
