/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import analizador.Analizador;
import areaController.ControladorDeRequerimientos;
import cargadorModelos.CargadorModelo;
import com.db4o.ObjectServer;
import evaluador.Evaluador;
import evaluador.ResultadoEvaluacion;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import modelo.Modelo;
import selectorDatos.SelectorDatos;
import selectorResultados.ResultadosDAO;

/**
 *
 * @author Santiago Avendaño
 */
public class PredictorManager implements Runnable {

    private static final long tiempoEspera = 5000;
    private volatile boolean keepTrying = true;
    private ControladorDeRequerimientos controladorAreas = new ControladorDeRequerimientos();
    private SelectorDatos selectorDatos;
    private Evaluador evaluador;
    private Analizador analizador = new Analizador();
    private CargadorModelo cargadorModelos;
    private ResultadosDAO resultadosDAO;
    
    private BlockingQueue<Modelo> salidaSubscriptor;
    private BlockingQueue<Collection<ResultadoEvaluacion>> salidaResultManager;

    public PredictorManager(ObjectServer server, ObjectServer serverResultados, String path) {
        cargadorModelos = new CargadorModelo(path);
        selectorDatos = new SelectorDatos(server);
        evaluador = new Evaluador(selectorDatos, controladorAreas);
        resultadosDAO = new ResultadosDAO(serverResultados);
    }

    public void setSalidaResultManager(BlockingQueue<Collection<ResultadoEvaluacion>> salidaResultManager) {
        this.salidaResultManager = salidaResultManager;
    }

    public void setSalidaSubscriptor(BlockingQueue<Modelo> salidaSubscriptor) {
        this.salidaSubscriptor = salidaSubscriptor;
    }

    @Override
    public void run() {
        Collection<Modelo> modelos;
        Collection<ResultadoEvaluacion> resultados;
        while (keepTrying) {
            try {
                modelos = cargadorModelos.getModelos();

                for (Modelo modelo : modelos) {
                    salidaSubscriptor.put(modelo);
                    System.out.println("[PM] ENVIE MODELO" + modelo.getNombreModelo());
                    resultados = evaluador.evaluar(modelo);
                    analizador.analizar(modelo, resultados);
                    salidaResultManager.put(resultados);
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
