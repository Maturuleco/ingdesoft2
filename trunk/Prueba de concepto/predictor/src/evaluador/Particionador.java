/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluador;

import java.util.Collection;
import java.util.LinkedList;
import predictor.Predictor;

/**
 *
 * @author Santiago Avendaño
 */
public class Particionador {

    private int tamanioParticion = 2;

    public Particionador(int tamanioParticion) {
        this.tamanioParticion = tamanioParticion;
        if (tamanioParticion <= 0)
            throw new IllegalArgumentException("El tamaño de la particion debe ser mayor a 0");
    }

    public Collection<Collection<PredictorThread>> particionar(Collection<Predictor> predictores, Contador contador) {
        Collection<Collection<PredictorThread>> resultado = new LinkedList<Collection<PredictorThread>>();
        Collection<PredictorThread> paquete = new LinkedList<PredictorThread>();
        for (Predictor predictor : predictores) {
            if (paquete.size() == tamanioParticion) {
                resultado.add(paquete);
                paquete = new LinkedList<PredictorThread>();
            }
            paquete.add(new PredictorThread(predictor, contador));
        }
        if (paquete.size() <= tamanioParticion) {
            resultado.add(paquete);
        }
        return resultado;
    }

}
