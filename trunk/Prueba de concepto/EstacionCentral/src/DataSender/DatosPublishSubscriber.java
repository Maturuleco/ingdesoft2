/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataSender;

import Datos.FactorClimatico;
import SubscripcionesEc.MensajePedidoSubscripcionDatos;
import SubscripcionesEc.SubscriberMessage;
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
        MensajePedidoSubscripcionDatos mensajeParticular = (MensajePedidoSubscripcionDatos)mensaje;
        Integer idSubscriptor = mensajeParticular.getIdSuscriptor();
        FactorClimatico factorClimatico =mensajeParticular.getFactorClimatico();
        Integer idTR = mensajeParticular.getTR();
        TRsFactoresSuscripcion suscripcion = new TRsFactoresSuscripcion(idSubscriptor, idTR, factorClimatico);
        return suscripcion;
    }
    
}
