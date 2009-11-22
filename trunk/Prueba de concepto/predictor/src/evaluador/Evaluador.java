/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluador;

import java.util.Collection;
import predictor.Predictor;

/**
 *
 * @author Santiago Avendaño
 */
public class Evaluador {

    private Particionador particionador = new Particionador(3);

    // TODO: implementar bien
    public Integer evaluar(Collection<Predictor> predictores) {
        Contador contador = new Contador();
        Collection<Collection<PredictorThread>> particiones = particionador.particionar(predictores,contador);
        for (Collection<PredictorThread> particion : particiones) {
            synchronized(contador) {
                evaluarSinParticionar(particion);
            }
        }
        return contador.getCantidad();
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
