
package modelo;

import RequerimientosModelos.RequerimientoResultado;
import RequerimientosModelos.RequerimientoDato;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Santiago Avendaño, Ce y Mat
 */
public class Modelo {

    private Integer nombreModelo;
    private Collection<Regla> reglas;
    private Set<RequerimientoDato> requerimientosDatos;
    private Set<RequerimientoResultado> requerimientosResultados;

    public Modelo(Integer nombreModelo, Collection<Regla> reglas) {
        this.nombreModelo = nombreModelo;
        this.reglas = reglas;
        this.requerimientosDatos = new HashSet<RequerimientoDato>();
        this.requerimientosResultados = new HashSet<RequerimientoResultado>();
    }

    public Collection<Regla> getReglas() {
        return reglas;
    }

    public Integer getNombreModelo() {
        return nombreModelo;
    }

    public Set<RequerimientoDato> getRequerimientosDatos() {
        return requerimientosDatos;
    }

    public Set<RequerimientoResultado> getRequerimientosResultados() {
        return requerimientosResultados;
    }

    public void setRequerimientosDatos(Set<RequerimientoDato> requerimientosDatos) {
        this.requerimientosDatos = requerimientosDatos;
    }

    public void setRequerimientosResultados(Set<RequerimientoResultado> requerimientosResultados) {
        this.requerimientosResultados = requerimientosResultados;
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
        if (this.nombreModelo != other.nombreModelo && (this.nombreModelo == null || !this.nombreModelo.equals(other.nombreModelo))) {
            return false;
        }
        if (this.reglas != other.reglas && (this.reglas == null || !this.reglas.equals(other.reglas))) {
            return false;
        }
        if (this.requerimientosDatos != other.requerimientosDatos && (this.requerimientosDatos == null || !this.requerimientosDatos.equals(other.requerimientosDatos))) {
            return false;
        }
        if (this.requerimientosResultados != other.requerimientosResultados && (this.requerimientosResultados == null || !this.requerimientosResultados.equals(other.requerimientosResultados))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.nombreModelo != null ? this.nombreModelo.hashCode() : 0);
        hash = 29 * hash + (this.reglas != null ? this.reglas.hashCode() : 0);
        hash = 29 * hash + (this.requerimientosDatos != null ? this.requerimientosDatos.hashCode() : 0);
        hash = 29 * hash + (this.requerimientosResultados != null ? this.requerimientosResultados.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String modeloView = "";
        modeloView += "ID modelo" + nombreModelo + "n";
        modeloView += "Requerimientos:\n";
        for (RequerimientoDato req : requerimientosDatos) {
            modeloView += req.toString() + "\n";
        }
        return super.toString();
    }
    
    
    
}
