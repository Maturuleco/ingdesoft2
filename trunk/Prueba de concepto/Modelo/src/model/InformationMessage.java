/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import SubscripcionesEc.SubscriberMessage;

/**
 *
 * @author mar
 */
public class InformationMessage implements SuscriptorMessage{
    Integer receptor;
    InformationMessage mensaje;

    public InformationMessage getMensaje() {
        return mensaje;
    }

    public void setMensaje(InformationMessage mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getReceptor() {
        return receptor;
    }

    public void setReceptor(Integer receptor) {
        this.receptor = receptor;
    }

    public InformationMessage(Integer receptor, InformationMessage mensaje) {
        this.receptor = receptor;
        this.mensaje = mensaje;
    }

    public boolean subscripcionAceptada() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SubscriberMessage getMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}