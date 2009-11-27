/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluador;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import predictor.Predictor;
import predictor.ResultadoRegla;

/**
 *
 * @author Santiago Avendaño
 */
public class Particionador {

    private int tamanioParticion = 2;

    public Particionador(int tamanioParticion) {
        if (tamanioParticion <= 0) {
            throw new IllegalArgumentException("El tamaño de la particion debe ser mayor a 0(por default se seteo el tamaño en 2)");
        } else {
            this.tamanioParticion = tamanioParticion;
        }
    }

    public Collection<Collection<PredictorThread>> particionar(Collection<Predictor> predictores, ConcurrentLinkedQueue<ResultadoRegla> resultados) {
        Collection<Collection<PredictorThread>> resultado = new LinkedList<Collection<PredictorThread>>();
        Collection<PredictorThread> paquete = new LinkedList<PredictorThread>();
        for (Predictor predictor : predictores) {
            if (paquete.size() == tamanioParticion) {
                resultado.add(paquete);
                paquete = new LinkedList<PredictorThread>();
            }
            paquete.add(new PredictorThread(predictor, resultados));
        }
        if (paquete.size() <= tamanioParticion) {
            resultado.add(paquete);
        }
        return resultado;
    }

}
