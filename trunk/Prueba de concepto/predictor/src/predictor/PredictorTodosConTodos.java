/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import java.util.Collection;
import model.DatoAlmacenado;
import model.FactorClimatico;
import modelo.Condicion;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class PredictorTodosConTodos extends Predictor {

    Collection<DatoAlmacenado> datosAlmacenados = null;

    public PredictorTodosConTodos() {
    }

    public PredictorTodosConTodos(Regla regla, Collection<DatoAlmacenado> datos) {
        super(regla);
        datosAlmacenados = datos;
    }

    // Analiza que el dato cumpla todas las condiciones
    @Override
    public ResultadoRegla analizar() {
        Collection<Condicion> condiciones = regla.getCondiciones();
        Integer condicionesVerificadas = 0;
        Integer condicionesNoAnalizadas = 0;
        Integer condicionesNoVerificadas = 0;

        for (Condicion condicion : condiciones) {
            if (hayDatoConTipo(datosAlmacenados, condicion.getFactor())) {
                if (analizar(condicion, datosAlmacenados)) {
                    condicionesVerificadas++;
                } else {
                    condicionesNoVerificadas++;
                }
            } else {
                condicionesNoAnalizadas++;
            }
        }
        return new ResultadoRegla(condicionesVerificadas, condicionesNoAnalizadas, condicionesNoVerificadas);
    }

    private Boolean hayDatoConTipo(Collection<DatoAlmacenado> datos, FactorClimatico factor) {
        for (DatoAlmacenado datoAlmacenado : datos) {
            if (datoAlmacenado.getFactor().equals(factor)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private Boolean analizar(Condicion condicion, Collection<DatoAlmacenado> datos) {
        for (DatoAlmacenado datoAlmacenado : datos) {
            if (!condicion.aplicar(datoAlmacenado)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
