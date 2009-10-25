/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Alerta;
import model.DatoSensado;

/**
 *
 * @author Santiago Avendaño
 */
public abstract class Predictor {    

    public List<Alerta> analizar(Map<Integer, Collection<DatoSensado>> datosSensados){
        Set<Integer> idTRs = datosSensados.keySet();
        List<Alerta> alertas = new LinkedList<Alerta>();
        for (Integer id : idTRs) {
            if (analizar(datosSensados.get(id))) {
                alertas.add(new Alerta(id, "Se detectó un huracán"));
            }
        }
        return alertas;
    }

    public abstract Boolean analizar(Collection<DatoSensado> datosAEvaluar);


}
