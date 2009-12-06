/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import java.util.Collection;
import java.util.Map;
import Datos.DatoAlmacenado;
import Datos.FactorClimatico;
import modelo.Condicion;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class PredictorPorFactorClimatico extends Predictor {

    private Map<FactorClimatico, Collection<DatoAlmacenado>> datos;

    public PredictorPorFactorClimatico(Regla regla, Map<FactorClimatico, Collection<DatoAlmacenado>> datos) {
        super(regla);
        this.datos = datos;
    }

    // Analiza que todos los datos cumplan todas las condiciones
    // Controlando por factor
    @Override
    public ResultadoRegla analizar() {
        Map<FactorClimatico, Collection<Condicion>> condicionesPorFactor;

        condicionesPorFactor = regla.condicionesPorFactor();
        Collection<Condicion> condicionesFactor;
        Collection<DatoAlmacenado> datosFactor;
        Integer condicionesVerificadas = 0;
        Integer condicionesNoAnalizadas = 0;
        Integer condicionesNoVerificadas = 0;
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
                    condicionesNoAnalizadas += condicionesFactor.size();
                } else {
                    for (Condicion condicion : condicionesFactor) {
                        if (analizar(condicion, datosFactor)) {
                            condicionesVerificadas++;
                        } else {
                            condicionesNoVerificadas++;
                        }
                    }
                }
            }
        }

        return new ResultadoRegla(condicionesVerificadas, condicionesNoAnalizadas, condicionesNoVerificadas);
    }

    // Analiza que el dato cumpla todas las condiciones
    private Boolean analizar(Condicion condicion, Collection<DatoAlmacenado> datosAlmacenados) {
        for (DatoAlmacenado datoAlmacenado : datosAlmacenados) {
            if (!condicion.aplicar(datoAlmacenado)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}



