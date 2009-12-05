/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ECsComunicator;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import model.FactorClimatico;
import model.SubscriptionAcceptedMessage;
import model.TRsSubscriberMessage;

/**
 *
 * @author mar
 */
public class ECsSubscriptor extends Thread{
    private BlockingQueue<TRsSubscriberMessage> salida;
    private BlockingQueue<SubscriptionAcceptedMessage> entrada;
    private Integer idEC;
    
    public ECsSubscriptor(Integer idEC) {
        this.idEC = idEC;
    }

    @Override
    public void run(){
        //mira archivo de configuracion
        //ejecuta subscribe mientras no reciba la rta
    }

    public void setEntrada(BlockingQueue<TRsSubscriberMessage> salida) {
        this.salida = salida;
    }

    public void setSalida (BlockingQueue<SubscriptionAcceptedMessage> entrada) {
        this.entrada = entrada;
    }

    public void subscribe (List<Integer> TRs, List<FactorClimatico> factores) {
        TRsSubscriberMessage mensaje = new TRsSubscriberMessage(this.idEC, new Date(), TRs, factores);
        salida.add(mensaje);
    }
}
