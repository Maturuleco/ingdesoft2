/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author mar
 */
public class InformationMessage<T> implements SuscriptorMessage {
    Integer receptor;
    T mensaje;

    public InformationMessage(Integer receptor, T mensaje) {
        this.receptor = receptor;
        this.mensaje = mensaje;
    }

    public T getMensaje() {
        return mensaje;
    }

    public Integer getReceptor() {
        return receptor;
    }
    
    @Override
    public String toString(){
        return receptor+ " - " + mensaje.toString();
    }
}
