/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EstadoDeRed;

import model.*;
import ModeloTerminal.CoordenadaGlobal;
import java.text.ParseException;
import java.util.Date;

public class MensajeRaise extends MensajeNetworkController implements MensajeGeneral {
    
    protected CoordenadaGlobal latitud;
    protected CoordenadaGlobal longitud;

    public MensajeRaise(int idTR, CoordenadaGlobal latitud, CoordenadaGlobal longitud) {
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
       respuesta += "|" + latitud.toString();
       respuesta += "|" + longitud.toString();
       
       return respuesta;
   }

    public static MensajeRaise parse(String mensaje) throws ParseException {
        String[] campos = mensaje.split("\\|");

        if( campos.length != 5 || !campos[0].equalsIgnoreCase("Raise")){
             throw new ParseException(mensaje, 0);
        }
        int id = Integer.valueOf(campos[1]);
        Date fecha = new Date(Long.valueOf(campos[2]));
        CoordenadaGlobal lat = CoordenadaGlobal.parse(campos[3]);
        CoordenadaGlobal lon = CoordenadaGlobal.parse(campos[4]);
        MensajeRaise res = new MensajeRaise(id, lat, lon);
        res.setFecha(fecha);
        return res;

    }

   
    
}
