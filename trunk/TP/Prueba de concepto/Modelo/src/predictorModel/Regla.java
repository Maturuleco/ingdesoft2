/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictorModel;

import java.util.Collection;

/**
 *
 * @author Santiago Avendaño
 */
public class Regla {
    private Collection<Condicion> condiciones;
    private String mensajePrediccion;

    public Regla(Collection<Condicion> condiciones, String prediccion) {
        this.condiciones = condiciones;
        this.mensajePrediccion = prediccion;
    }

    public Collection<Condicion> getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(Collection<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    public String getMensajePrediccion() {
        return mensajePrediccion;
    }

    public void setMensajePrediccion(String prediccion) {
        this.mensajePrediccion = prediccion;
    }
}
