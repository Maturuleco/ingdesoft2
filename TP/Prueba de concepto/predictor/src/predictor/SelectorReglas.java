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
        condiciones1.add(new Condicion(FactorClimatico.humedad, Comparador.mayor, 90));
        condiciones1.add(new Condicion(FactorClimatico.velocidad_viento, Comparador.mayor, 80));
        condiciones2.add(new Condicion(FactorClimatico.humedad, Comparador.mayor, 90));
        Regla regla1 = new Regla(condiciones1, "Se detecto un huracan");
        Regla regla2 = new Regla(condiciones2, "Se detectaron lluvia fuertes");
        reglas.add(regla1);
        reglas.add(regla2);
        return reglas;
    }

}
