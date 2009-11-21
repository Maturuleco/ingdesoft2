/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.text.ParseException;
import java.util.Date;

public class MensajeRaise extends MensajeNetworkController {

    public MensajeRaise(String mensaje) throws ParseException {
        String[] campos = mensaje.split("#");

        if( campos.length != 4 || !"RAISE".equalsIgnoreCase(campos[0])){
             throw new ParseException(mensaje, 0);
        }
        setIdTR(Integer.valueOf(campos[1]));
        setFecha(new Date(Long.valueOf(campos[2])));

    }

   
    
}
