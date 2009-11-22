/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ECsComunicator;

import java.util.concurrent.BlockingQueue;
import model.SubscriptionAcceptedMessage;
import model.TRsSubscriberMessage;

/**
 *
 * @author mar
 */
public class PublishSubscribe extends Thread{

    private BlockingQueue<TRsSubscriberMessage> entrada;
    private BlockingQueue<SubscriptionAcceptedMessage> salida;

    @Override
    public void run(){

    }
}
