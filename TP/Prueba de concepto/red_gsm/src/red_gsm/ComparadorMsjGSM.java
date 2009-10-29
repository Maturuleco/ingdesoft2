/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package red_gsm;

import java.util.Comparator;

/**
 *
 * @author Santiago Avendaño
 */
public class ComparadorMsjGSM implements Comparator<MensajeGSM> {

        public int compare(MensajeGSM m1, MensajeGSM m2) {
            return (m1.getPriority() - m2.getPriority());
        }

}
