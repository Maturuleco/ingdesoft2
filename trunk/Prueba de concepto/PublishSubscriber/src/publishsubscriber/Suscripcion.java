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
public abstract class Suscripcion<T> {
    Integer idSuscriptor;

    public Suscripcion(Integer idSuscriptor) {
        this.idSuscriptor = idSuscriptor;
    }

    
    public Integer getIdSuscriptor() {
        return idSuscriptor;
    }

    public void setIdSuscriptor(Integer idSuscriptor) {
        this.idSuscriptor = idSuscriptor;
    }

    public abstract Boolean seCorresponde(T mensaje);

    @Override
    public abstract boolean equals(Object obj);

}
