/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizador;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Administrador
 */
public class Prediccion {

    public static final Prediccion PREDICCION_NULA = new Prediccion();

    private Integer idmodelo;
    private String descripcion;
    private Float probabilidad;
    private Set<Integer> idTrs;

    public Prediccion() {
        idmodelo = null;
        descripcion = "";
        probabilidad = 0.0F;
        idTrs = new HashSet<Integer>();
    }

    public Prediccion(Integer idmodelo, String descripcion, float probabilidad, Set<Integer> idTrs) {
        this.idmodelo = idmodelo;
        this.descripcion = descripcion;
        this.probabilidad = probabilidad;
        this.idTrs = idTrs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prediccion other = (Prediccion) obj;
        if (this.idmodelo != other.idmodelo && (this.idmodelo == null || !this.idmodelo.equals(other.idmodelo))) {
            return false;
        }
        if ((this.descripcion == null) ? (other.descripcion != null) : !this.descripcion.equals(other.descripcion)) {
            return false;
        }
        if (this.probabilidad != other.probabilidad && (this.probabilidad == null || !this.probabilidad.equals(other.probabilidad))) {
            return false;
        }
        if (this.idTrs != other.idTrs && (this.idTrs == null || !this.idTrs.equals(other.idTrs))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.idmodelo != null ? this.idmodelo.hashCode() : 0);
        hash = 19 * hash + (this.descripcion != null ? this.descripcion.hashCode() : 0);
        hash = 19 * hash + (this.probabilidad != null ? this.probabilidad.hashCode() : 0);
        hash = 19 * hash + (this.idTrs != null ? this.idTrs.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String mostrar = "=================PREDICCION=================\n";
        mostrar += "ID modelo: " + idmodelo +"\n";
        mostrar += "Descripcion: " + descripcion + "\n";
        mostrar += "Probabilidad: " + probabilidad + "%\n";
        mostrar += "Terminales analizadas: ";
        for (Integer id : idTrs) {
            mostrar += id.toString() + ", ";
        }
        return mostrar;
    }




}
