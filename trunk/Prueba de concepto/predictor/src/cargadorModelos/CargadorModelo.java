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
         //TODO: implementar bien
        Collection<Regla> reglas = new LinkedList<Regla>();
        Collection<Condicion> condiciones1 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones2 = new LinkedList<Condicion>();
        condiciones1.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, 0.0f));
        Regla regla1 = new Regla(condiciones1, "SE DETECTO FRIO POLAR");
        reglas.add(regla1);
        return reglas;
    }
}
