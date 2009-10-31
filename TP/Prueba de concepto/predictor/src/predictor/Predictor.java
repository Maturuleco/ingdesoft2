/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import excepciones.InsuficienciaDeDatosException;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Condicion;
import java.util.Map;
import model.DatoAlmacenado;
import model.FactorClimatico;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño
 */
public class Predictor implements Runnable {

    private Regla regla;
    private Map<FactorClimatico, Collection<DatoAlmacenado>> datos;

    public Predictor(Regla regla, Map<FactorClimatico, Collection<DatoAlmacenado>> datos) {
        this.regla = regla;
        this.datos = datos;
    }

    public Boolean analizar() {
        Boolean res = Boolean.FALSE;
        try {
             res = analizarCondicionesPorFactor();
        } catch (InsuficienciaDeDatosException ex) {
            Logger.getLogger(Predictor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public void run() {
        Boolean detectoAlerta = analizar();
        if (detectoAlerta) {
            //TODO: escribir en un archivo
            System.out.println(Calendar.getInstance().getTime().toString());
            System.out.println(regla.getMensajePrediccion());
        } else {
            //TODO: hacer algo
            System.out.println("NO" + regla.getMensajePrediccion());
        }
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

    // Analiza que todos los datos cumplan todas las condiciones
    // Controlando por factor
    private Boolean analizarCondicionesPorFactor() throws InsuficienciaDeDatosException {
        Map<FactorClimatico, Collection<Condicion>> condicionesPorFactor;

        condicionesPorFactor = regla.condicionesPorFactor();
        Collection<Condicion> condicionesFactor;
        Collection<DatoAlmacenado> datosFactor;
        for (FactorClimatico factor : FactorClimatico.values()) {
            condicionesFactor = condicionesPorFactor.get(factor);
            datosFactor = datos.get(factor);
            if (datosFactor == null ||condicionesFactor == null) {
                throw  new NullPointerException("Tanto el diccionario de " +
                        "condicionesPorFactor como el de datosPorFactor deben " +
                        "tener definidas todas las claves correspondientes a los Factores ");
            }
            // Si no tengo condiciones para ese dato no analizo los datos
            if (!condicionesFactor.isEmpty()) {
                //Si tengo condiciones sobre los datos:
                //  1) debe haber un dato al que se le pueda aplicar la condicion
                //  2) Todos deben todas las condiciones
                if (datosFactor.isEmpty()) {
                    throw new InsuficienciaDeDatosException("No hay suficientes datos para analizar la regla");
                } else {
                    if (!analizar(condicionesFactor, datosFactor)) {
                        return Boolean.FALSE;
                    }
                }
            }
        }

        return Boolean.TRUE;
    }
}
