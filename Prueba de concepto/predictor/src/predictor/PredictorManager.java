/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import areaController.AreaController;
import cargadorModelos.CargadorModelo;
import com.db4o.ObjectServer;
import predictorFactory.PredictorFactory;
import predictorFactory.PredictorAgruparPorFactorFactory;
import evaluador.Evaluador;
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
    private SelectorDatos selectorDatos;
    private CargadorModelo cargadorModelos;
    private AreaController controladorAreas = new AreaController();
    private PredictorFactory predictorFactory = new PredictorAgruparPorFactorFactory();
    private Evaluador evaluador = new Evaluador();

    public PredictorManager(ObjectServer server) {
        selectorDatos = new SelectorDatos(server);
        cargadorModelos = new CargadorModelo();
    }

    @Override
    public void run() {
        Collection<Modelo> modelos;
        List<DatoAlmacenado> datosTotales;
        Map< Integer, List<DatoAlmacenado>> datosTotalesAgrupadosPorTR;
        List<Predictor> predictores;
        Area areaInfluenciaModelo;
        Collection<Integer> trsSeleccionadas;


        while (keepTrying) {
            try {
                modelos = cargadorModelos.getModelos();

                for (Modelo modelo : modelos) {
                    
                    areaInfluenciaModelo = modelo.getArea();
                    trsSeleccionadas = controladorAreas.buscarTerminalesRemotas(areaInfluenciaModelo);
                    datosTotales = selectorDatos.seleccionar(trsSeleccionadas, null, Integer.SIZE);
                    datosTotalesAgrupadosPorTR = selectorDatos.agruparDatosPorTR(datosTotales);
                    for (Integer idTR : trsSeleccionadas) {
                        predictores = predictorFactory.obtenerPredictores(modelo, datosTotalesAgrupadosPorTR.get(idTR));
                        evaluador.evaluar(predictores);
                    }
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
