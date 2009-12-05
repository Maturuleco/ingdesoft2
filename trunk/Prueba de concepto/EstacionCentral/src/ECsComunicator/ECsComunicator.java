/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ECsComunicator;

/**
 *
 * @author mar
 */
public class ECsComunicator extends Thread{

    private ECsPublishSubscriber publishSubscribe;
    private ECsSubscriptor subscriptor;
    private Integer idEC;

    ECsComunicator(Integer idEC){
        this.publishSubscribe = new ECsPublishSubscriber();
        this.idEC = idEC;
        this.subscriptor = new ECsSubscriptor(this.idEC);
    }

    @Override
    public void run() {
        publishSubscribe.run();
        subscriptor.run();
    }
}
