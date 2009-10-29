/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package validator;

import com.db4o.ObjectServer;
import java.util.Calendar;
import java.util.Date;
import model.DataSource;
import model.DatoAlmacenado;
import model.FactorClimatico;

/**
 *
 * @author Santiago Avendaño
 */
public class ValidatorManager implements Runnable{

    private static final long tiempoEspera = 1000;
    private volatile boolean keepTrying = true;

    private ValidatorDAO validadorDAO = null;
    private Validador validador = new Validador();

    public ValidatorManager(ObjectServer server) {
        validadorDAO = new ValidatorDAO(server);
    }

    @Override
    public void run() {
        while (keepTrying) {
            try {
                //TODO: completar cuando lleguen datos
                Date timeStamp = Calendar.getInstance().getTime();
                DatoAlmacenado dato = new DatoAlmacenado(1, timeStamp, FactorClimatico.temperatura, 20.0F, 1, DataSource.terminal_remota);
                if (validador.validar(dato)){
                    validadorDAO.escribirDatosValidos(dato);
                } else {
                    validadorDAO.escribirDatosOutlier(dato);
                }
                System.out.println(timeStamp.toString()+"guarde un dato en la base");
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException ex) { }
        }
    }

}
