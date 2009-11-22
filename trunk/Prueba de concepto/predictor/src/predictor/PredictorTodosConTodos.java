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
    public Boolean analizar() {
        Collection<Condicion> condiciones = regla.getCondiciones();
        for (Condicion condicion : condiciones) {
            if ( hayDatoConTipo(datosAlmacenados, condicion.getFactor())) {
                for (DatoAlmacenado datoAlmacenado : datosAlmacenados) {
                    if (!condicion.aplicar(datoAlmacenado)) {
                        return Boolean.FALSE;
                    }
                }
            } else {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    private Boolean hayDatoConTipo(Collection<DatoAlmacenado> datos, FactorClimatico factor) {
        for (DatoAlmacenado datoAlmacenado : datos) {
            if (datoAlmacenado.getFactor().equals(factor)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
