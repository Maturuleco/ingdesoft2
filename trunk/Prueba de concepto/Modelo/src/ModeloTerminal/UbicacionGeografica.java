/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ModeloTerminal;

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
    
}
