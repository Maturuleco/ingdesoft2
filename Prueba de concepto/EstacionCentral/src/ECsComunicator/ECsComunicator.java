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

    private PublishSubscribe publishSubscribe;

    ECsComunicator(){
        this.publishSubscribe = new PublishSubscribe();
    }

    @Override
    public void run() {
        publishSubscribe.run();
    }
}
