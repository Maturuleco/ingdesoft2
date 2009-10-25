/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trmatias;

import java.util.List;

import gsm.MensajeGSM;
import gsm.MensajeGSM;
import gsm.ModemGSM;
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
        int id = Mensaje.id;
        modem.send(estacionCentral, Mensaje.toString());
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
