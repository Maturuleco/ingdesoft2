/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package particionador;

import evaluador.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import predictor.Predictor;
import predictor.ResultadoRegla;

/**
 *
 * @author Santiago Avendaño
 */
public interface Particionador {

    public Collection<Collection<PredictorThread>> particionar(Collection<Predictor> predictores, ConcurrentLinkedQueue<ResultadoRegla> resultados);

}
