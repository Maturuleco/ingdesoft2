/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TR;

import java.util.List;

import red_gsm.MensajeGSM;
import red_gsm.ModemGSM;
import model.Mensaje;

/**
 *
 * @author Administrador
 */
public class DataSender {
    private ModemGSM modem;
    private int estacionCentral = 0;
    private List<Integer> enviados;

    public void send(Mensaje m)
    {
        //Acá se haría la particion de los msjs
        int id = m.getIdTR();
        modem.send(estacionCentral, m.toString());
        enviados.add(id);
    }

    public Boolean recive(MensajeGSM msj)
    {
        if (msj.getOrigen() != estacionCentral)
            return false;
        else
        {
            Mensaje respuesta = new Mensaje(MensajeGSM);
            
        }
    }
}
