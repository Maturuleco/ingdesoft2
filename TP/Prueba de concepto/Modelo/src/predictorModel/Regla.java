/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictorModel;

import model.DatoSensado;
import model.FactorClimatico;

/**
 *
 * @author Santiago Avendaño
 */
public class Regla {
    private Comparador comparador;
    private Comparable constante;
    private FactorClimatico factor;

    public Regla(Comparador comparador, Comparable constante, FactorClimatico factor) {
        this.comparador = comparador;
        this.constante = constante;
        this.factor = factor;
    }

    public Comparador getComparador() {
        return comparador;
    }

    public Comparable getConstante() {
        return constante;
    }

    public FactorClimatico getFactor() {
        return factor;
    }

    public boolean aplicar(DatoSensado dato){
        boolean res = true;

        if (dato.getFactor().equals(factor)) {
            switch (comparador){
                case igual:
                    return constante.compareTo(dato.getValor()) == 0;
                case menor:
                    return constante.compareTo(dato.getValor()) > 0;
                case mayor:
                    return constante.compareTo(dato.getValor()) < 0;
                case mayeq:
                    return (constante.compareTo(dato.getValor()) < 0) ||  (constante.compareTo(dato.getValor()) == 0);
                case meneq:
                    return (constante.compareTo(dato.getValor()) > 0) ||  (constante.compareTo(dato.getValor()) == 0);
                default :
                    throw new UnsupportedOperationException("El operador " + comparador + "no esá soportado");
            }
        }
        return res;
    }

}
