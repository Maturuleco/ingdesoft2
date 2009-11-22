/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictor;

import java.util.Collection;
import java.util.Map;
import model.DatoAlmacenado;
import model.FactorClimatico;
import modelo.Condicion;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class PredictorPorTipo extends Predictor {

    private Map<FactorClimatico, Collection<DatoAlmacenado>> datos;

    public PredictorPorTipo(Regla regla, Map<FactorClimatico, Collection<DatoAlmacenado>> datos, String lugar) {
        super(regla);
        this.datos = datos;
    }

    @Override
    public Boolean analizar() {
        Boolean res = Boolean.FALSE;
        res = analizarCondicionesPorFactor();
        return res;
    }

    // Analiza que todos los datos cumplan todas las condiciones
    // Controlando por factor
    private Boolean analizarCondicionesPorFactor() {
        Map<FactorClimatico, Collection<Condicion>> condicionesPorFactor;

        condicionesPorFactor = regla.condicionesPorFactor();
        Collection<Condicion> condicionesFactor;
        Collection<DatoAlmacenado> datosFactor;
        for (FactorClimatico factor : FactorClimatico.values()) {
            condicionesFactor = condicionesPorFactor.get(factor);
            datosFactor = datos.get(factor);
            if (datosFactor == null || condicionesFactor == null) {
                throw new NullPointerException("Tanto el diccionario de " +
                        "condicionesPorFactor como el de datosPorFactor deben " +
                        "tener definidas todas las claves correspondientes a los Factores ");
            }
            // Si no tengo condiciones para ese dato no analizo los datos
            if (!condicionesFactor.isEmpty()) {
                //Si tengo condiciones sobre los datos:
                //  1) debe haber un dato al que se le pueda aplicar la condicion
                //  2) Todos deben todas las condiciones
                if (datosFactor.isEmpty()) {
                    return Boolean.FALSE;
                } else {
                    if (!analizar(condicionesFactor, datosFactor)) {
                        return Boolean.FALSE;
                    }
                }
            }
        }

        return Boolean.TRUE;
    }

    // Analiza que el dato cumpla todas las condiciones
    private Boolean analizar(Collection<Condicion> condiciones, Collection<DatoAlmacenado> datosAlmacenados) {
        for (DatoAlmacenado datoAlmacenado : datosAlmacenados) {
            for (Condicion condicion : condiciones) {
                if (!condicion.aplicar(datoAlmacenado)) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }

}
