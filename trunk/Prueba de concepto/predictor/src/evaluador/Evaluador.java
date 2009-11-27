/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluador;

import java.awt.geom.Area;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import model.DatoAlmacenado;
import modelo.Modelo;
import predictor.Predictor;
import predictor.ResultadoRegla;

/**
 *
 * @author Santiago Avendaño
 */
public class Evaluador {

    private Particionador particionador = new Particionador(3);

    public ConcurrentLinkedQueue<ResultadoRegla> evaluar(Modelo modelo){
        ConcurrentLinkedQueue<ResultadoRegla> resultados = new ConcurrentLinkedQueue<ResultadoRegla>();

        List<DatoAlmacenado> datosTotales;
        Map< Integer, List<DatoAlmacenado>> datosTotalesAgrupadosPorTR;
        List<Predictor> predictores;
        Area areaInfluenciaModelo;
        Collection<Integer> trsSeleccionadas;
        return resultados;
    }

    // TODO: implementar bien
    public ConcurrentLinkedQueue<ResultadoRegla> evaluar(Collection<Predictor> predictores) {
        ConcurrentLinkedQueue<ResultadoRegla> resultados = new ConcurrentLinkedQueue<ResultadoRegla>();
        Collection<Collection<PredictorThread>> particiones = particionador.particionar(predictores,resultados);
        for (Collection<PredictorThread> particion : particiones) {
                evaluarSinParticionar(particion);
        }
        return resultados;
    }

    private void evaluarSinParticionar(Collection<PredictorThread> particion) {

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
