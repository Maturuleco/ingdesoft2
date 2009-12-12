/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import evaluador.ResultadoEvaluacion;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import modelo.Modelo;

/**
 *
 * @author Santiago Avendaño
 */
public class Analizador {

    public Prediccion analizar(Modelo modelo, Collection<ResultadoEvaluacion> resultados) {
        Integer reglasVerificadasTotales = 0;
        Integer reglasEvaluadasTotales = 0;
        Prediccion prediccionResultado ;
        Set<Integer> trsEvaluadas = new HashSet<Integer>();


        for (ResultadoEvaluacion resultadoEvaluacion : resultados) {
            reglasEvaluadasTotales += resultadoEvaluacion.getReglasEvaluadas();
            reglasVerificadasTotales += resultadoEvaluacion.getReglasVerificadas();
            trsEvaluadas.add(resultadoEvaluacion.getIdTR());
        }

        Float porcentaje;
        if (!reglasEvaluadasTotales.equals(0)) {
            porcentaje = Float.valueOf(reglasVerificadasTotales.toString()) / Float.valueOf(reglasEvaluadasTotales.toString());
        } else {
            porcentaje = 0.0F;
        }
        porcentaje = porcentaje * 100.0F;


        // Si la probabilidad es mayor a 50 se genera una alerta
        if (porcentaje >= 50) {
            prediccionResultado = new Prediccion(modelo.getNombreModelo(), modelo.getDescripcion(), porcentaje, trsEvaluadas);
        } else {
            prediccionResultado = Prediccion.PREDICCION_NULA;
        }

        return prediccionResultado;
    }
}
