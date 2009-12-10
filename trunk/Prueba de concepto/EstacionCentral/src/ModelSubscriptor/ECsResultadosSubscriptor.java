/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ModelSubscriptor;

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
    public void run(){

    }
}
