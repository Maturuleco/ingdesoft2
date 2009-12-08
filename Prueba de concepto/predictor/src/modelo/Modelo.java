
package modelo;

import java.util.Collection;

/**
 *
 * @author Santiago Avendaño, Ce y Mat
 */
public class Modelo {

    private Integer nombreModelo;
    private Collection<Regla> reglas;
    private Requerimiento requerimientos;

    public Modelo(Integer nombreModelo, Collection<Regla> reglas, Requerimiento requerimientos) {
        this.nombreModelo = nombreModelo;
        this.reglas = reglas;
        this.requerimientos = requerimientos;
    }

    public Requerimiento getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(Requerimiento requerimientos) {
        this.requerimientos = requerimientos;
    }
  
    public Collection<Regla> getReglas() {
        return reglas;
    }

    public Integer getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(Integer nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Modelo other = (Modelo) obj;
        if (this.nombreModelo.equals(other.nombreModelo) && (this.nombreModelo == null || !this.nombreModelo.equals(other.nombreModelo))) {
            return false;
        }
        if (this.reglas != other.reglas && (this.reglas == null || !this.reglas.equals(other.reglas))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.nombreModelo != null ? this.nombreModelo.hashCode() : 0);
        hash = 13 * hash + (this.reglas != null ? this.reglas.hashCode() : 0);
        return hash;
    }
    
}
