/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictor;

import java.util.Collection;
import predictorModel.Regla;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import model.DatoSensado;
import model.FactorClimatico;

/**
 *
 * @author Santiago Avendaño
 */
public class Predictor {


    // Analiza que todos los datos cumplan todas las reglas
    // Controlando todos con todos
    public Boolean analizar(Collection<Regla> reglas, Collection<DatoSensado> datos){
        for (Regla regla : reglas) {
            for (DatoSensado dato  : datos) {
                if (!regla.aplicar(dato)) return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
    
    // Analiza que el dato cumpla todas las reglas
    public Boolean analizar(Collection<Regla> reglas, DatoSensado dato){
        for (Regla regla : reglas) {
            if (!regla.aplicar(dato)) return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    // Analiza q la regla se satisfaga en todos los datos
     public Boolean analizar(Regla regla, Collection<DatoSensado> datos){
         for (DatoSensado dato : datos) {
             if (!regla.aplicar(dato)) return Boolean.FALSE;
         }
         return Boolean.TRUE;
    }

    // Analiza que todos los datos cumplan todas las reglas
    // Controlando por factor
    public Boolean analizarReglasPorFactor(Collection<Regla> reglas, Collection<DatoSensado> datos){
        Map<FactorClimatico, Collection<Regla>> reglasPorFactor;

        reglasPorFactor = ordenarPorFactor(reglas);

        for (DatoSensado dato: datos) {
            if (analizar(reglasPorFactor.get(dato.getFactor()), dato)) return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Map<FactorClimatico, Collection<Regla>> ordenarPorFactor(Collection<Regla> reglas){
        Map<FactorClimatico, Collection<Regla>> result = new EnumMap<FactorClimatico, Collection<Regla>>(FactorClimatico.class);
        
        for (FactorClimatico factor : FactorClimatico.values()) {
            result.put(factor, new LinkedList<Regla>() );
        }

        for (Regla regla : reglas) {
            result.get(regla.getFactor()).add(regla);
        }

        return result;
    }



}
