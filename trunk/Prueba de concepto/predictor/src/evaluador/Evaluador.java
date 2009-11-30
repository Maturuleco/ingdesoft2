/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluador;

import areaController.AreaController;
import java.awt.geom.Area;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import model.DatoAlmacenado;
import modelo.Modelo;
import predictor.Predictor;
import predictor.ResultadoRegla;
import predictorFactory.PredictorAgruparPorFactorFactory;
import predictorFactory.PredictorFactory;
import selectorDatos.SelectorDatos;

/**
 *
 * @author Santiago Avendaño
 */
public class Evaluador {

    private SelectorDatos selectorDatos;
    private AreaController controladorAreas;
    private PredictorFactory predictorFactory = new PredictorAgruparPorFactorFactory();
    private Particionador particionador = new Particionador(3);

    public Evaluador(SelectorDatos selectorDatos, AreaController controladorAreas) {
        this.selectorDatos = selectorDatos;
        this.controladorAreas = controladorAreas;
    }

    public Collection<ResultadoEvaluacion> evaluar(Modelo modelo) {
        Collection<ResultadoEvaluacion> resultados = new LinkedList();
        List<DatoAlmacenado> datosTotales;
        Map<Integer, List<DatoAlmacenado>> datosTotalesAgrupadosPorTR;
        List<Predictor> predictores;
        Area areaInfluenciaModelo;
        Set<Integer> trsSeleccionadas;

        areaInfluenciaModelo = modelo.getArea();
        trsSeleccionadas = controladorAreas.buscarTerminalesRemotas(areaInfluenciaModelo);
        datosTotales = selectorDatos.seleccionar(trsSeleccionadas, null, 10);
        datosTotalesAgrupadosPorTR = selectorDatos.agruparDatosPorTR(datosTotales);
        for (Integer idTR : datosTotalesAgrupadosPorTR.keySet()) {
            predictores = predictorFactory.obtenerPredictores(modelo, datosTotalesAgrupadosPorTR.get(idTR));
            resultados.add(empaquetarResultado(modelo.getNombreModelo(),idTR, evaluar(predictores)));
        }
        return resultados;
    }

    private ResultadoEvaluacion empaquetarResultado(String nombreModelo, Integer idTR,ConcurrentLinkedQueue<ResultadoRegla> resultados ){
        Integer reglasVerificadas = 0;
        for (ResultadoRegla resultadoRegla : resultados) {
            if (resultadoRegla.verifiqueTodasLasCondiciones()) reglasVerificadas++;
        }
        ResultadoEvaluacion resultado =new ResultadoEvaluacion();
        resultado.setNombreModelo(nombreModelo);
        resultado.setIdTR(idTR);
        resultado.setReglasEvaluadas(resultados.size());
        resultado.setReglasVerificadas(reglasVerificadas);
        return resultado ;
    }
    // TODO: implementar bien
    private ConcurrentLinkedQueue<ResultadoRegla> evaluar(Collection<Predictor> predictores) {
        ConcurrentLinkedQueue<ResultadoRegla> resultados = new ConcurrentLinkedQueue<ResultadoRegla>();
        Collection<Collection<PredictorThread>> particiones = particionador.particionar(predictores, resultados);
        for (Collection<PredictorThread> particion : particiones) {
            ejecutarPredictores(particion);
        }
        return resultados;
    }

    private void ejecutarPredictores(Collection<PredictorThread> particion) {

        // Inicio el thread
        for (PredictorThread predictorThread : particion) {
            predictorThread.start();
        }

        // Me bloqueo hasta que terminen todos los threads
        for (PredictorThread predictorThread : particion) {
            try {
                predictorThread.join();
            } catch (InterruptedException ex) {
                System.out.println("El thread " + predictorThread.getName() + " fue interrumpido\n");
                System.out.println(ex.getMessage());
            }
        }
    }
}
