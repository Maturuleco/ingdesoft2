/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SubscriptorModelos;

import Datos.FactorClimatico;
import RequerimientosModelos.RequerimientoDato;
import RequerimientosModelos.RequerimientoResultado;
import com.thoughtworks.xstream.XStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import modelo.Comparador;
import modelo.Condicion;
import modelo.Modelo;
import modelo.Regla;

/**
 *
 * @author tas
 */
public class MainTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        
//        Collection<Modelo> modelos = new LinkedList<Modelo>();

        Modelo modelo1 = new Modelo(69, "se detectaron vientos fuertes", getReglas1());
        //modelos.add(modelo1);
        Modelo modelo2 = new Modelo(7070, "se detectó frio polar", getReglas2());
        
        modelo1.setRequerimientosDatos(getReqDato1());
        modelo1.setRequerimientosResultados(new HashSet<RequerimientoResultado>());
        modelo2.setRequerimientosDatos(new HashSet<RequerimientoDato>());
        modelo2.setRequerimientosResultados(getReqResult1());
        
//        SubscriptorModelos subs = new SubscriptorModelos();
//        BlockingQueue<Modelo> entradaModelos = new LinkedBlockingQueue<Modelo>();
//
//        subs.setEntradaModelos(entradaModelos);
//        entradaModelos.add(modelo1);
//        entradaModelos.add(modelo2);
//
//        subs.run();
        

        XStream xstream = new XStream();
        String xml1 = xstream.toXML(modelo1);
        String xml2 = xstream.toXML(modelo2);
        System.out.println("XML1:\n"+xml1+"\n");
        System.out.println("XML2:\n"+xml2+"\n");
        Modelo modelo1bis = (Modelo)xstream.fromXML(xml1);
        Modelo modelo2bis = (Modelo)xstream.fromXML(xml2);
        System.out.println("Son iguales?..."+modelo1.equals(modelo1bis));
        System.out.println("Y ahora que le cambié el nombre?..."+modelo1.equals(modelo1bis));

        //modelos.add(modelo2);
    }

    private static Set<RequerimientoDato> getReqDato1() {
        Set<RequerimientoDato> reqs = new HashSet<RequerimientoDato>();
        reqs.add(new RequerimientoDato(01, 01, FactorClimatico.lluvias));
        reqs.add(new RequerimientoDato(01, 02, FactorClimatico.lluvias));
        reqs.add(new RequerimientoDato(02, 03, FactorClimatico.temperatura));
        reqs.add(new RequerimientoDato(03, 04, FactorClimatico.humedad));
        return reqs;
    }
    
    private static Set<RequerimientoResultado> getReqResult1() {
        Set<RequerimientoResultado> reqs = new HashSet<RequerimientoResultado>();
        reqs.add(new RequerimientoResultado(01, 69, 01));
        reqs.add(new RequerimientoResultado(01, 69, 02));
        reqs.add(new RequerimientoResultado(01, 7070, 04));
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
