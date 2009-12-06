/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ECsComunicator;

import java.util.ArrayDeque;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import Datos.FactorClimatico;
import model.SubscriptionAcceptedMessage;
import model.TRsSubscriberMessage;

/**
 *
 * @author mar
 */
public class PublishSubscribe extends Thread{

    private static final long sleepTime = 100;
    private BlockingQueue<TRsSubscriberMessage> entrada;
    private BlockingQueue<SubscriptionAcceptedMessage> salida;
    // Por TR, por factor climatico, la lista de ECs suscriptas
    private Dictionary<Integer, Dictionary<FactorClimatico, List<Integer>>> suscripciones;

    public PublishSubscribe() {
        suscripciones = new Hashtable<Integer, Dictionary<FactorClimatico, List<Integer>>>();
        //TODO: llenar con las trs y los factores climaticos que manejen
    }

    @Override
    public void run(){
        while (true) {
            if (! sensarEntradaDatos() ) {
                try {
                    // Duermo un segundo
                    sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }

    public void setEntrada(BlockingQueue<TRsSubscriberMessage> entrada) {
        this.entrada = entrada;
    }

    public void setSalida (BlockingQueue<SubscriptionAcceptedMessage> salida) {
        this.salida = salida;
    }

    private boolean sensarEntradaDatos() {
        TRsSubscriberMessage mensaje = entrada.poll();
        if (mensaje != null) {
            System.out.println("El Publish Subscriber recibe una suscripcion");
            for (Integer TR : mensaje.getTRs()){
                for (FactorClimatico factor : mensaje.getFactoresClimaticos()){
                    suscripciones.get(TR).get(factor).add(mensaje.getIdEC());
                }
            }
            SubscriptionAcceptedMessage respuesta = new SubscriptionAcceptedMessage(mensaje);
            enviarRespuesta(respuesta);
            return true;
        }
        return false;
    }

    private void enviarRespuesta(SubscriptionAcceptedMessage respuesta){
        salida.add(respuesta);
        System.out.println("El Publish Subscriber acepta una suscripcion");
    }
}
