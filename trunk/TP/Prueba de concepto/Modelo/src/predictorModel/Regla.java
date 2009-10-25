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
                    res = constante.compareTo(dato.getValor()) == 0;
                    break;
                case menor:
                    res = constante.compareTo(dato.getValor()) > 0;
                    break;
                case mayor:
                    res = constante.compareTo(dato.getValor()) < 0;
                    break;
                case mayeq:
                    res = (constante.compareTo(dato.getValor()) < 0) ||  (constante.compareTo(dato.getValor()) == 0);
                    break;
                case meneq:
                    res = (constante.compareTo(dato.getValor()) > 0) ||  (constante.compareTo(dato.getValor()) == 0);
                    break;
                default :
                    throw new UnsupportedOperationException("El operador " + comparador + "no esá soportado");
            }
        }
        return res;
    }

}
