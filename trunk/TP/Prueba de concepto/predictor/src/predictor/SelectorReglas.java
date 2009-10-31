/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictor;

import java.util.Collection;
import java.util.LinkedList;
import model.FactorClimatico;
import modelo.Comparador;
import modelo.Condicion;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño
 */
public class SelectorReglas {


    public Collection<Regla> getReglas(){
        //TODO: implementar bien
        Collection<Regla> reglas = new LinkedList<Regla>();
        Collection<Condicion> condiciones1 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones2 = new LinkedList<Condicion>();
        condiciones1.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, 0.0f));
        Regla regla1 = new Regla(condiciones1, "se detecto frío polar");
        reglas.add(regla1);
        return reglas;
    }

}
