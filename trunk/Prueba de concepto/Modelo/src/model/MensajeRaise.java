/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.text.ParseException;
import java.util.Date;

public class MensajeRaise extends MensajeNetworkController implements MensajeGeneral {
    
    protected long latitud;
    protected long longitud;

    public MensajeRaise(int idTR, long latitud, long longitud) {
        this.fecha = new Date();
        this.idTR = idTR;
        this.latitud = latitud;
        this.longitud = longitud;
    }
    
    
    public long getTimeStamp() {
        return this.fecha.getTime();
    }
    
   @Override 
    public String toString(){
       String respuesta = "Raise";
       respuesta += "|" + idTR;
       respuesta += "|" + fecha.getTime();
       respuesta += "|" + latitud;
       respuesta += "|" + longitud;
       
       return respuesta;
   }

    public static MensajeRaise parse(String mensaje) throws ParseException {
        String[] campos = mensaje.split("\\|");

        if( campos.length != 5 || !campos[0].equalsIgnoreCase("Raise")){
             throw new ParseException(mensaje, 0);
        }
        int id = Integer.valueOf(campos[1]);
        Date fecha = new Date(Long.valueOf(campos[2]));
        int lat = Integer.valueOf(campos[3]);
        int lon = Integer.valueOf(campos[4]);
        MensajeRaise res = new MensajeRaise(id, lat, lon);
        res.setFecha(fecha);
        return res;

    }

   
    
}
