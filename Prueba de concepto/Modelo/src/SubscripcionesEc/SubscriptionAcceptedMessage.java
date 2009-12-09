/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SubscripcionesEc;

import model.*;

/**
 *
 * @author mar
 */
public class SubscriptionAcceptedMessage implements SuscriptorMessage{
    private SubscriberMessage mensajeAceptado;

    public SubscriptionAcceptedMessage(SubscriberMessage mensajeAceptado) {
        this.mensajeAceptado = mensajeAceptado;
    }

    public SubscriberMessage getMensajeAceptado() {
        return mensajeAceptado;
    }

    public boolean subscripcionAceptada() {
        return true;
    }

    public SubscriberMessage getMessage() {
        return mensajeAceptado;
    }

}
