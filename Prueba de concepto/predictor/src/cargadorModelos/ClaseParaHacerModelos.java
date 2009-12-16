/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cargadorModelos;

import Datos.FactorClimatico;
import RequerimientosModelos.RequerimientoDato;
import RequerimientosModelos.RequerimientoResultado;
import com.thoughtworks.xstream.XStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import modelo.Condicion;
import modelo.Comparador;
import modelo.Modelo;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño
 */
public class ClaseParaHacerModelos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Modelo modelo = new Modelo(4,"", getReglas1());

        //modelo.setRequerimientosDatos(getReqDato());
        //modelo.setRequerimientosResultados(getReqResult());

        Collection<Modelo> modelos = new LinkedList<Modelo>();

        Modelo modelo1 = new Modelo(1, "se detectaron vientos fuertes", getReglas1());
        Modelo modelo2 = new Modelo(2,  "se detectó frio polar", getReglas2());

        modelo1.setRequerimientosDatos(getReqDato1());
        modelo1.setRequerimientosResultados(new HashSet<RequerimientoResultado>());
        modelo2.setRequerimientosDatos(new HashSet<RequerimientoDato>());
        modelo2.setRequerimientosResultados(getReqResult2());

        modelos.add(modelo1);
        modelos.add(modelo2);

        XStream xstream = new XStream();
        String xml = xstream.toXML(modelo2);

        System.out.println("\nBegin Modelo\n");
        System.out.println(xml);
        System.out.println("\nEnd Modelo\n");
    }

    private static Set<RequerimientoDato> getReqDato() {
        Set<RequerimientoDato> reqs = new HashSet<RequerimientoDato>();
        reqs.add(new RequerimientoDato(01, 01, FactorClimatico.temperatura));
        reqs.add(new RequerimientoDato(01, 01, FactorClimatico.humedad));
        return reqs;
    }

    private static Set<RequerimientoResultado> getReqResult() {
        Set<RequerimientoResultado> reqs = new HashSet<RequerimientoResultado>();
        reqs.add(new RequerimientoResultado(01, 01, 01));
        reqs.add(new RequerimientoResultado(01, 02, 01));
        reqs.add(new RequerimientoResultado(01, 03, 01));
        return reqs;
    }

     private static Set<RequerimientoDato> getReqDato1() {
        Set<RequerimientoDato> reqs = new HashSet<RequerimientoDato>();
        reqs.add(new RequerimientoDato(02, 01, FactorClimatico.lluvias));
        reqs.add(new RequerimientoDato(02, 02, FactorClimatico.lluvias));
        reqs.add(new RequerimientoDato(02, 03, FactorClimatico.temperatura));
        reqs.add(new RequerimientoDato(02, 04, FactorClimatico.humedad));
        return reqs;
    }

    private static Set<RequerimientoResultado> getReqResult2() {
        Set<RequerimientoResultado> reqs = new HashSet<RequerimientoResultado>();
        reqs.add(new RequerimientoResultado(01, 1, 01));
        reqs.add(new RequerimientoResultado(01, 1, 02));
        reqs.add(new RequerimientoResultado(01, 2, 04));
        return reqs;
    }

    private static Collection<Regla> getReglas1() {
        Collection<Regla> reglas = new LinkedList<Regla>();
        Collection<Condicion> condiciones1 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones2 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones3 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones4 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones5 = new LinkedList<Condicion>();
        condiciones1.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, 0.0f));
        condiciones1.add(new Condicion(FactorClimatico.presion, Comparador.mayor, 1020.0f));
        condiciones1.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, -10.0f));
        condiciones2.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, -10.0f));
        condiciones3.add(new Condicion(FactorClimatico.humedad, Comparador.menor, 50.0f));
        condiciones4.add(new Condicion(FactorClimatico.velocidad_viento, Comparador.mayor, 40.0f));
        condiciones4.add(new Condicion(FactorClimatico.direccion_viento, Comparador.igual, 0.0f));
        condiciones5.add(new Condicion(FactorClimatico.lluvias, Comparador.mayor, 30.0f));
        reglas.add(new Regla("Regla 1", condiciones1));
        reglas.add(new Regla("Regla 2", condiciones2));
        reglas.add(new Regla("Regla 3", condiciones3));
        reglas.add(new Regla("Regla 4", condiciones4));
        reglas.add(new Regla("Regla 5", condiciones5));

        return reglas;
    }

    private static Collection<Regla> getReglas2() {
        Collection<Regla> reglas = new LinkedList<Regla>();
        Collection<Condicion> condicionesTemperatura = new LinkedList<Condicion>();
        Collection<Condicion> condicionesHumedad = new LinkedList<Condicion>();
        condicionesTemperatura.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, 0.0f));
        condicionesTemperatura.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, -10.0f));
        condicionesTemperatura.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, -20.0f));
        condicionesHumedad.add(new Condicion(FactorClimatico.humedad, Comparador.menor, 60.0f));
        condicionesHumedad.add(new Condicion(FactorClimatico.humedad, Comparador.menor, 50.0f));
        condicionesHumedad.add(new Condicion(FactorClimatico.humedad, Comparador.menor, 40.0f));

        reglas.add(new Regla("Regla 1", condicionesTemperatura));
        reglas.add(new Regla("Regla 2", condicionesHumedad));

        return reglas;
    }

}
