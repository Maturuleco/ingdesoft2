/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictorFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import model.DatoAlmacenado;
import modelo.Modelo;
import modelo.Regla;
import predictor.Predictor;
import predictor.PredictorTodosConTodos;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class PredictorTodosConTodosFactory implements PredictorFactory {

    @Override
    public List<Predictor> obtenerPredictores(Modelo modelo, Collection<DatoAlmacenado> datos) {
        Collection<Regla> reglas = modelo.getReglas();
        List<Predictor> listaPredictores = new LinkedList<Predictor>();
        for (Regla regla : reglas) {
            listaPredictores.add(new PredictorTodosConTodos(regla, datos));
        }
        return listaPredictores;
    }
}
