/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictorFactory;

import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.DatoAlmacenado;
import model.FactorClimatico;
import modelo.Modelo;
import modelo.Regla;
import predictor.Predictor;
import predictor.PredictorPorFactorClimatico;

/**
 *
 * @author Santiago Avendaño
 */
public class PredictorAgruparPorFactorFactory implements PredictorFactory{

    @Override
    public List<Predictor> obtenerPredictores(Modelo modelo, Collection<DatoAlmacenado> datos) {
        Collection<Regla> reglas = modelo.getReglas();
        List<Predictor> listaPredictores = new LinkedList<Predictor>();
        Map<FactorClimatico, Collection<DatoAlmacenado>> datosPorFactor = agruparDatosPorFactor(datos);
        for (Regla regla : reglas) {
            listaPredictores.add(new PredictorPorFactorClimatico(regla, datosPorFactor));
        }
        return listaPredictores;
    }


    private Map<FactorClimatico, Collection<DatoAlmacenado>> agruparDatosPorFactor(Collection<DatoAlmacenado> datos) {
        Map<FactorClimatico, Collection<DatoAlmacenado>> result = new EnumMap<FactorClimatico, Collection<DatoAlmacenado>>(FactorClimatico.class);

        for (FactorClimatico factor : FactorClimatico.values()) {
            result.put(factor, new LinkedList<DatoAlmacenado>());
        }

        for (DatoAlmacenado datoAlmacenado : datos) {
            result.get(datoAlmacenado.getFactor()).add(datoAlmacenado);
        }

        return result;
    }
}
