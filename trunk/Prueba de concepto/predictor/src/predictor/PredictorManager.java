/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictor;

import com.db4o.ObjectServer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import model.DatoAlmacenado;
import model.FactorClimatico;
import modelo.Regla;
import selectorDatos.SelectorDatos;

/**
 *
 * @author Santiago Avendaño
 */
public class PredictorManager implements Runnable {

    private static final long tiempoEspera = 5000;
    private volatile boolean keepTrying = true;

    private SelectorDatos selectorDatos;
    private SelectorReglas selectorReglas;

    public PredictorManager(ObjectServer server) {
        selectorDatos = new SelectorDatos(server);
        selectorReglas = new SelectorReglas();
    }

    @Override
    public void run() {
        while (keepTrying) {
            try {
                Map<Integer, List<DatoAlmacenado>> datosPorTR = selectorDatos.datosPorTR();
                Collection<Regla> reglas = selectorReglas.getReglas();
                List<DatoAlmacenado> datosTR;
                Predictor predictor;
                for (Integer idTR : datosPorTR.keySet()) {
                    datosTR = datosPorTR.get(idTR);
                    Map<FactorClimatico,Collection<DatoAlmacenado>> datos
                            = SelectorDatos.ordenarPorFactor(datosTR);

                    for (Regla regla : reglas) {
                        predictor = new Predictor(regla, datos, "TR"+idTR.toString());
                        new Thread(predictor).run();
                    }
                }
                
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException ex) { }
        }
    }

    public void requestStop(){
        keepTrying = false;
    }

}
