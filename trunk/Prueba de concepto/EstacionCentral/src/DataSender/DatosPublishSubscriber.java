/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataSender;

import model.*;
import publishsubscriber.PublishSubscriber;
import publishsubscriber.Suscripcion;
import publishsubscriber.TRsFactoresSuscripcion;


/**
 *
 * @author mar
 */
public class DatosPublishSubscriber extends PublishSubscriber{

    public DatosPublishSubscriber() {

    }

    @Override
    protected Suscripcion crearSuscripcion(SubscriberMessage mensaje) {
        TRsSubscriberMessage mensajeParticular = (TRsSubscriberMessage)mensaje;
        TRsFactoresSuscripcion suscripcion = new TRsFactoresSuscripcion();
        suscripcion.setIdSuscriptor(mensajeParticular.getIdSuscriptor());
        suscripcion.setFactores(mensajeParticular.getFactoresClimaticos());
        suscripcion.setIdsTR(mensajeParticular.getTRs());
        return suscripcion;
    }
    
}
