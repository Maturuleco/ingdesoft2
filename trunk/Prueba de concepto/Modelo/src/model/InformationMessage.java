/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;

/**
 *
 * @author mar
 */
public class InformationMessage<T> implements Serializable {
    Integer receptor;
    T mensaje;

    public InformationMessage(Integer receptor, T mensaje) {
        this.receptor = receptor;
        this.mensaje = mensaje;
    }

    public T getMensaje() {
        return mensaje;
    }

//    public void setMensaje(InformationMessage mensaje) {
//        this.mensaje = mensaje;
//    }

    public Integer getReceptor() {
        return receptor;
    }

//    public void setReceptor(Integer receptor) {
//        this.receptor = receptor;
//    }
    public String toString() {
        return mensaje.toString();
    }
}
