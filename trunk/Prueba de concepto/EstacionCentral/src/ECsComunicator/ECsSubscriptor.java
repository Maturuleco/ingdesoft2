/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ECsComunicator;

import SubscripcionesEc.SubscriberMessage;
import publishsubscriber.Subscriptor;

/**
 *
 * @author mar
 */
public class ECsSubscriptor extends Subscriptor{

    private Integer idEC;

    public ECsSubscriptor(Integer idEC) {
        this.idEC = idEC;
    }

    @Override
    protected SubscriberMessage crearMensajeSuscripcion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
