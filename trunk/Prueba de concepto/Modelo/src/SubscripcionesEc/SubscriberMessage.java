/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SubscripcionesEc;

/**
 *
 * @author mar
 */
public abstract class SubscriberMessage {

    Integer idSuscriptor;
    Integer ecProovedora;

    public SubscriberMessage(Integer idSuscriptor, Integer ecProovedora) {
        this.idSuscriptor = idSuscriptor;
        this.ecProovedora = ecProovedora;
    }

    public Integer getEcProovedora() {
        return ecProovedora;
    }

    public Integer getIdSuscriptor() {
        return idSuscriptor;
    }
    
}
