/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ModeloTerminal;

import java.text.ParseException;

/**
 *
 * @author tas
 */
public class UbicacionGeografica {
    CoordenadaGlobal latitud;
    CoordenadaGlobal longitud;

    public UbicacionGeografica(CoordenadaGlobal latitud, CoordenadaGlobal longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public CoordenadaGlobal getLatitud() {
        return latitud;
    }

    public CoordenadaGlobal getLongitud() {
        return longitud;
    }
    
    @Override
    public String toString(){
        String salida;
        salida = "Lat·"+latitud.toString()+"|";
        salida += "Long·"+longitud.toString();
        return salida;
    }
    
    public static UbicacionGeografica parse(String entrada) throws ParseException{
       String[] partes = entrada.split("\\|");
       UbicacionGeografica res = null;
       if (partes.length != 2)
           throw new ParseException(entrada, 0);
       else{
           String[] latitud = partes[0].split("·");
           String[] longitud = partes[1].split("·");
           if (latitud.length != 2 || longitud.length != 2)
               throw new ParseException(entrada, 0);
           CoordenadaGlobal lat = CoordenadaGlobal.parse(latitud[1]);
           CoordenadaGlobal lon = CoordenadaGlobal.parse(longitud[1]);
           res = new UbicacionGeografica(lat, lon);
       }
       return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UbicacionGeografica other = (UbicacionGeografica) obj;
        if (this.latitud != other.latitud && (this.latitud == null || !this.latitud.equals(other.latitud))) {
            return false;
        }
        if (this.longitud != other.longitud && (this.longitud == null || !this.longitud.equals(other.longitud))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.latitud != null ? this.latitud.hashCode() : 0);
        hash = 97 * hash + (this.longitud != null ? this.longitud.hashCode() : 0);
        return hash;
    }
    
    
    
}
