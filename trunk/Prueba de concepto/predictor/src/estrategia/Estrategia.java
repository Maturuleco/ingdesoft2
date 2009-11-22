/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estrategia;

import java.util.Collection;
import java.util.List;
import model.DatoAlmacenado;
import modelo.Modelo;
import predictor.Predictor;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public interface Estrategia {

    public List<Predictor> obtenerPredictores(Modelo modelo, Collection<DatoAlmacenado> datos);
}
