
package modelo;

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
    
}
