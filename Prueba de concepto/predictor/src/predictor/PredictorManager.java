/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import analizador.Analizador;
import areaController.AreaController;
import cargadorModelos.CargadorModelo;
import com.db4o.ObjectServer;
import predictorFactory.PredictorFactory;
import predictorFactory.PredictorAgruparPorFactorFactory;
import evaluador.Evaluador;
import evaluador.ResultadoEvaluacion;
import java.awt.geom.Area;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    private AreaController controladorAreas = new AreaController();
    private SelectorDatos selectorDatos;
    private Evaluador evaluador;
    private Analizador analizador = new Analizador();
    private CargadorModelo cargadorModelos = new CargadorModelo();

    public PredictorManager(ObjectServer server, ObjectServer serverResultados) {
        selectorDatos = new SelectorDatos(server);
        evaluador = new Evaluador(selectorDatos, controladorAreas);
        analizador.getAnalizadorDAO().setServerResultados(serverResultados);
    }

    @Override
    public void run() {
        Collection<Modelo> modelos;
        Collection<ResultadoEvaluacion> resultados;
        while (keepTrying) {
            try {
                modelos = cargadorModelos.getModelos();

                for (Modelo modelo : modelos) {
                    resultados = evaluador.evaluar(modelo);
                    analizador.analizar(modelo, resultados);
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
