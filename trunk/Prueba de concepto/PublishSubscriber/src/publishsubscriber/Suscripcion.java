/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package publishsubscriber;

import model.SuscriptorMessage;

/**
 *
 * @author mar
 */
public abstract class Suscripcion {
    Integer idSuscriptor;

    public Integer getIdSuscriptor() {
        return idSuscriptor;
    }

    public void setIdSuscriptor(Integer idSuscriptor) {
        this.idSuscriptor = idSuscriptor;
    }

    public abstract Boolean seCorresponde(SuscriptorMessage mensaje);

}