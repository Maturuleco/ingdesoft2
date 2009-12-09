/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ModelSubscriptor;

import SubscripcionesEc.SubscriberMessage;
import publishsubscriber.Subscriptor;

/**
 *
 * @author mar
 */
public class ECsResultadosSubscriptor extends Subscriptor{

    private Integer idEC;

    public ECsResultadosSubscriptor(Integer idEC) {
        this.idEC = idEC;
    }

    @Override
    protected SubscriberMessage crearMensajeSuscripcion() {
        //se fija en que necesitan los modelos en ese momento
        //y crea un ResultadosSubscriberMessage para pedir los
        //resultados parciales de los otros modelos
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
