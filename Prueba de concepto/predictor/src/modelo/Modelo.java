/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Collection;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class Modelo {

    private String nombreModelo;
    private Collection<Regla> reglas;

    public Collection<Regla> getReglas() {
        return reglas;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

}
