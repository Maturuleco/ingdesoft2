/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictor;

import java.util.Collection;
import modelo.Condicion;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import model.DatoSensado;
import model.FactorClimatico;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño
 */
public class Predictor {


    // Analiza que todos los datos cumplan todas las condiciones
    // Controlando todos con todos
    public Boolean analizar(Regla regla, Collection<DatoSensado> datos){
        Collection<Condicion> condiciones = regla.getCondiciones();
        for (Condicion condicion : condiciones) {
            for (DatoSensado dato  : datos) {
                if (!condicion.aplicar(dato)) return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
    
    // Analiza que el dato cumpla todas las condiciones
    public Boolean analizar(Collection<Condicion> condiciones, DatoSensado dato){
        for (Condicion condicion : condiciones) {
            if (!condicion.aplicar(dato)) return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    // Analiza q la condicion se satisfaga en todos los datos
     public Boolean analizar(Condicion condicion, Collection<DatoSensado> datos){
         for (DatoSensado dato : datos) {
             if (!condicion.aplicar(dato)) return Boolean.FALSE;
         }
         return Boolean.TRUE;
    }

    // Analiza que todos los datos cumplan todas las condiciones
    // Controlando por factor
    public Boolean analizarCondicionesPorFactor(Collection<Condicion> condiciones, Collection<DatoSensado> datos){
        Map<FactorClimatico, Collection<Condicion>> condicionesPorFactor;

        condicionesPorFactor = ordenarPorFactor(condiciones);

        for (DatoSensado dato: datos) {
            if (analizar(condicionesPorFactor.get(dato.getFactor()), dato)) return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Map<FactorClimatico, Collection<Condicion>> ordenarPorFactor(Collection<Condicion> condiciones){
        Map<FactorClimatico, Collection<Condicion>> result = new EnumMap<FactorClimatico, Collection<Condicion>>(FactorClimatico.class);
        
        for (FactorClimatico factor : FactorClimatico.values()) {
            result.put(factor, new LinkedList<Condicion>() );
        }

        for (Condicion condicion : condiciones) {
            result.get(condicion.getFactor()).add(condicion);
        }

        return result;
    }



}
