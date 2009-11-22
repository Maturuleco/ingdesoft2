/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cargadorModelos;

import java.util.Collection;
import java.util.LinkedList;
import model.FactorClimatico;
import modelo.Comparador;
import modelo.Condicion;
import modelo.Modelo;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño
 */
public class CargadorModelo {

    public Collection<Modelo> getModelos(){
        Collection<Modelo> modelos = new LinkedList<Modelo>();
        Modelo modelo1 = new Modelo("FRIO POLAR", getReglas());
        modelos.add(modelo1);
        return modelos;
    }

    private Collection<Regla> getReglas(){
        Collection<Regla> reglas = new LinkedList<Regla>();
        Collection<Condicion> condiciones1 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones2 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones3 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones4 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones5 = new LinkedList<Condicion>();
        condiciones1.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, 0.0f));
        condiciones1.add(new Condicion(FactorClimatico.presion, Comparador.mayor, 1020.0f));
        condiciones2.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, -10.0f));
        condiciones3.add(new Condicion(FactorClimatico.humedad, Comparador.menor, 50.0f));
        condiciones4.add(new Condicion(FactorClimatico.velocidad_viento, Comparador.mayor, 40.0f));
        condiciones4.add(new Condicion(FactorClimatico.direccion_viento, Comparador.igual, 0.0f));
        condiciones5.add(new Condicion(FactorClimatico.lluvias, Comparador.mayor, 30.0f));
        reglas.add(new Regla("Regla 1",condiciones1));
        reglas.add(new Regla("Regla 2",condiciones2));
        reglas.add(new Regla("Regla 3",condiciones3));
        reglas.add(new Regla("Regla 4",condiciones4));
        reglas.add(new Regla("Regla 5",condiciones5));

        return reglas;
    }
}
