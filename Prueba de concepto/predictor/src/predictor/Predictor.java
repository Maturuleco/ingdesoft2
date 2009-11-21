/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import excepciones.InsuficienciaDeDatosException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
    private String lugar;

    public Predictor(Regla regla, Map<FactorClimatico, Collection<DatoAlmacenado>> datos, String lugar) {
        this.regla = regla;
        this.datos = datos;
        this.lugar = lugar;
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
            escribirPrediccion();
            System.out.println(Calendar.getInstance().getTime().toString());
            System.out.println("<<" + regla.getMensajePrediccion()+ ">>");
        } else {
            System.out.println("<<NO " + regla.getMensajePrediccion()+">>");
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
                    return Boolean.FALSE;
                } else {
                    if (!analizar(condicionesFactor, datosFactor)) {
                        return Boolean.FALSE;
                    }
                }
            }
        }

        return Boolean.TRUE;
    }

    private void escribirPrediccion() {
        String directorio = "Predicciones";
        Date ahora = Calendar.getInstance().getTime();
        String nombre = "Alerta";
        nombre += String.valueOf(ahora.getTime());
        nombre += lugar;
        nombre +=  regla.getMensajePrediccion();
        try {
            new File(directorio).mkdir();
            FileWriter fw = new FileWriter(directorio+"/"+nombre+".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            salida.append(" ============ ALERTA ============\n");
            salida.append("Fecha: " + ahora.toString() + "\n");
            salida.append("Lugar: " + lugar + "\n");
            salida.append("Mensaje: " + regla.getMensajePrediccion() + "\n");
            salida.append(" ================================");
            salida.close();
        } catch (java.io.IOException ioex) {
            System.out.println("se presento el error: " + ioex.toString());
        }
    }
}
