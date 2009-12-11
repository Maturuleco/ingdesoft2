package ResultSender;

import SubscripcionesEc.MensajePedidoSubscripcionResultados;
import SubscripcionesEc.SubscriberMessage;
import publishsubscriber.ModeloTrSuscripcion;
import publishsubscriber.PublishSubscriber;
import publishsubscriber.Suscripcion;

/*
 * @author Ce
 */
public class ResultSender extends PublishSubscriber{

    @Override
    protected Suscripcion crearSuscripcion(SubscriberMessage mensaje) {
        MensajePedidoSubscripcionResultados mensajeParticular = (MensajePedidoSubscripcionResultados)mensaje;

        Integer idSubscriptor = mensajeParticular.getIdSuscriptor();
        Integer modeloID = mensajeParticular.getModelo();
        Integer trID = mensajeParticular.getTrID();

        ModeloTrSuscripcion suscripcion = new ModeloTrSuscripcion(idSubscriptor, modeloID, trID);
        return suscripcion;
    }

}
