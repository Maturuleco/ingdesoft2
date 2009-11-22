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

    private int cantidadPorPaquete = 2;

    public Collection<Collection<Predictor>> particionar(Collection<Predictor> predictores) {
        Collection<Collection<Predictor>> resultado = new LinkedList<Collection<Predictor>>();
        Collection<Predictor> paquete = new LinkedList<Predictor>();
        for (Predictor predictor : predictores) {
            if (paquete.size() == cantidadPorPaquete) {
                resultado.add(paquete);
                paquete = new LinkedList<Predictor>();
            }
            paquete.add(predictor);
        }
        if (paquete.size() == cantidadPorPaquete) {
                resultado.add(paquete);
        }
        return resultado;
    }

}
