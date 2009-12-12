!
package RequerimientosModelos;

import Datos.FactorClimatico;
import SubscripcionesEc.CreadorPedidosSubscripciones;
import SubscripcionesEc.SubscriberMessage;

/**
 *
 * @author Ce y Mat
 */
public class RequerimientoDato extends Requerimiento {

    private Integer trID;
    private FactorClimatico factor;

    public RequerimientoDato(Integer ecProveedora, Integer trID, FactorClimatico factor) {
        super(ecProveedora);
        this.trID = trID;
        this.factor = factor;
    }

    public FactorClimatico getFactor() {
        return factor;
    }

    public Integer getTrID() {
        return trID;
    }

    @Override
    public SubscriberMessage crearSubscripcion(Integer idSubscriptor, CreadorPedidosSubscripciones creador) {
        return creador.crearPedidoDatos(idSubscriptor, this);
    }

    @Override
    public String toString() {
        return "Requeriemiento: TR = " + trID + " Factor = " + factor.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RequerimientoDato other = (RequerimientoDato) obj;
        if (this.trID != other.trID && (this.trID == null || !this.trID.equals(other.trID))) {
            return false;
        }
        if (this.factor != other.factor) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.trID != null ? this.trID.hashCode() : 0);
        hash = 71 * hash + this.factor.hashCode();
        return hash;
    }

    

}
