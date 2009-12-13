/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package particionador;

import evaluador.PredictorThread;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import predictor.Predictor;
import predictor.ResultadoRegla;

/**
 *
 * @author Administrador
 */
public class ParticionadorPorTiempo implements Particionador{

    public Collection<Collection<PredictorThread>> particionar(Collection<Predictor> predictores, ConcurrentLinkedQueue<ResultadoRegla> resultados) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
