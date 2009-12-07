/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author mar
 */
public class SubscriptionAcceptedMessage extends SuscriptorMessage{
    private SubscriberMessage mensajeAceptado;

    public SubscriptionAcceptedMessage(SubscriberMessage mensajeAceptado) {
        this.mensajeAceptado = mensajeAceptado;
    }

    
}
