/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictor;

import java.util.Collection;
import java.util.Map;
import model.DatoSensado;

/**
 *
 * @author Santiago Avendaño
 */
public class Predictor {
    Map<Integer, Collection<DatoSensado> > datosSensados;


    public void analizar(){
        Collection<Integer> idTRs = datosSensados.keySet();
        for (Integer id : idTRs) {
            if (analizarHuracanes(datosSensados.get(id))) {
                System.out.println("Huracán detectado en la TR: " + id.toString());
            }
        }
    }

    public Boolean analizarHuracanes(Collection<DatoSensado> datosAEvaluar){
        final Integer limite_velocidad = 60;
        final Integer limite_humedad = 90;
        final Integer limite_temperatura = 28;
        for (DatoSensado datoSensado : datosAEvaluar) {
            switch(datoSensado.getFactor()){
                case velocidad_viento:
                    if (datoSensado.getValor() < limite_velocidad) return Boolean.FALSE;
                    break;
                case humedad:
                    if (datoSensado.getValor() < limite_humedad ) return Boolean.FALSE;
                    break;
                case temperatura:
                    if (datoSensado.getValor() <  limite_temperatura) return Boolean.FALSE;
                    break;
            }
        }
        return Boolean.TRUE;
    }


}
