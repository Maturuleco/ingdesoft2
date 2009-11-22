/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import cargadorModelos.CargadorModelo;
import com.db4o.ObjectServer;
import estrategia.Estrategia;
import estrategia.EstrategiaAgruparPorFactor;
import evaluador.Evaluador;
import java.util.Collection;
import java.util.List;
import model.DatoAlmacenado;
import modelo.Modelo;
import selectorDatos.SelectorDatos;

/**
 *
 * @author Santiago Avendaño
 */
public class PredictorManager implements Runnable {

    private static final long tiempoEspera = 5000;
    private volatile boolean keepTrying = true;
    private SelectorDatos selectorDatos;
    private CargadorModelo cargadorModelos;
    private Estrategia estrategia = new EstrategiaAgruparPorFactor();
    private Evaluador evaluador = new Evaluador();

    public PredictorManager(ObjectServer server) {
        selectorDatos = new SelectorDatos(server);
        cargadorModelos = new CargadorModelo();
    }

    @Override
    public void run() {
        while (keepTrying) {
            try {
                List<DatoAlmacenado> datos = selectorDatos.leerTodosLosDatos();
                Collection<Modelo> modelos = cargadorModelos.getModelos();
                List<Predictor> predictores;

                for (Modelo modelo : modelos) {
                    predictores = estrategia.obtenerPredictores(modelo, datos);
                    evaluador.evaluar(predictores);
                }

                Thread.sleep(tiempoEspera);
            } catch (InterruptedException ex) {
                System.out.println("El thread Predictor Manager fue interrumpido");
            }
        }
    }

    public void requestStop() {
        keepTrying = false;
    }
}
