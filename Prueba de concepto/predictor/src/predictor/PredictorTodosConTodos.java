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
    public ResultadoAnalisis analizar() {
        Collection<Condicion> condiciones = regla.getCondiciones();
        ResultadoAnalisis respuesta = new ResultadoAnalisis();
        for (Condicion condicion : condiciones) {
            if (hayDatoConTipo(datosAlmacenados, condicion.getFactor())) {
                for (DatoAlmacenado datoAlmacenado : datosAlmacenados) {
                    if (!condicion.aplicar(datoAlmacenado)) {
                        respuesta.setVerifiqueTodos(Boolean.FALSE);
                        return respuesta;
                    }
                }
            } else {
                respuesta.setVerifiqueTodos(Boolean.FALSE);
                return respuesta;
            }
        }
        respuesta.setVerifiqueTodos(Boolean.TRUE);
        return respuesta;
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
