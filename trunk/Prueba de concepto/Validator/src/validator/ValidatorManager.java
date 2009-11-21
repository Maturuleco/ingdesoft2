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
                /*
                    //TODO: completar cuando lleguen datos
                    Date timeStamp = Calendar.getInstance().getTime();
                    DatoAlmacenado dato = new DatoAlmacenado(1, timeStamp, FactorClimatico.temperatura, 20.0F, 1, DataSource.terminal_remota);
                */
                    if (validador.validar(dato)){
                        validadorDAO.escribirDatosValidos(dato);
                    } else {
                        validadorDAO.escribirDatosOutlier(dato);
                    }
//                    System.out.println(dato.toString()+": guarde un dato en la base");
                    Thread.sleep(tiempoEspera);
                }
            } catch (InterruptedException ex) { }
        }
    }

}
