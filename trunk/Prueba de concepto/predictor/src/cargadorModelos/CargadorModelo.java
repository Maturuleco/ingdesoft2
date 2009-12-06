/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cargadorModelos;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.LinkedList;
import Datos.FactorClimatico;
import modelo.Comparador;
import modelo.Condicion;
import modelo.Modelo;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño
 */
public class CargadorModelo {

    public Collection<Modelo> getModelos() {
        Collection<Modelo> modelos = new LinkedList<Modelo>();
        Area area1 = new Area(new Rectangle(0, 0, 10, 10));
        Modelo modelo1 = new Modelo("FRIO", getReglas1(), area1);
        //modelos.add(modelo1);
        Modelo modelo2 = new Modelo("FRIO POLAR", getReglas2(), area1);
        modelos.add(modelo2);
        return modelos;
    }

    private Collection<Regla> getReglas1() {
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

    private Collection<Regla> getReglas2() {
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
