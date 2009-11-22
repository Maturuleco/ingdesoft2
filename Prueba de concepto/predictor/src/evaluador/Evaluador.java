/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluador;

import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import predictor.Predictor;

/**
 *
 * @author Santiago Avendaño
 */
public class Evaluador {

    // TODO: implementar bien
    public Boolean evaluar(Collection<Predictor> predictores) {
        return Boolean.TRUE;
    }

    private Boolean evaluarSinParticionar(Collection<Predictor> particion) {
        Thread threadPredictor;
        Collection<Thread> threads = new LinkedList<Thread>();
        // Inicio el thread
        for (Predictor predictor : particion) {
            threadPredictor = new Thread(predictor);
            threads.add(threadPredictor);
            threadPredictor.start();
        }

        // Me bloqueo hasta que terminen todos los threads
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                System.out.println("El thread " + thread.getName() + " fue interrumpido\n");
                System.out.println(ex.getMessage());
            }
        }

        
        return Boolean.TRUE;
    }
}
