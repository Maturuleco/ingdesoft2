/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictor;

import java.util.Collection;
import model.DatoAlmacenado;
import modelo.Regla;
import selectorDatos.SelectorDatos;

/**
 *
 * @author Santiago Avendaño
 */
public class PredictorManager implements Runnable {

    private static final long tiempoEspera = 10000;
    private volatile boolean keepTrying = true;

    private SelectorDatos selectorDatos;
    private SelectorReglas selectorReglas;

    public PredictorManager(SelectorDatos selectorDatos, SelectorReglas selectorReglas) {
        this.selectorDatos = selectorDatos;
        this.selectorReglas = selectorReglas;
    }

    @Override
    public void run() {
        while (keepTrying) {
            try {
                Collection<DatoAlmacenado> datos = selectorDatos.leerTodosLosDatos();
                Collection<Regla> reglas = selectorReglas.getReglas();
                Predictor predictor;
                for (Regla regla : reglas) {
                     predictor = new Predictor(regla, datos);
                     new Thread(predictor).run();
                }
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException ex) { }
        }
    }

    public void requestStop(){
        keepTrying = false;
    }

}
