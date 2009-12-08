/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ECsComunicator;

import model.SubscriberMessage;
import publishsubscriber.Subscriptor;

/**
 *
 * @author mar
 */
public class ECsDatosSubscriptor extends Subscriptor{

    private Integer idEC;

    public ECsDatosSubscriptor(Integer idEC) {
        this.idEC = idEC;
    }

    @Override
    protected SubscriberMessage crearMensajeSuscripcion() {
        //se fija en que necesitan los modelos en ese momento
        //y crea un TRsSubscriberMessage para los datos de tr
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
