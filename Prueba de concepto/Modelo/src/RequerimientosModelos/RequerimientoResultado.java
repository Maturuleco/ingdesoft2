package RequerimientosModelos;

import SubscripcionesEc.CreadorPedidosSubscripciones;
import SubscripcionesEc.SubscriberMessage;

/**
 *
 * @author Ce y Mat
 */
public class RequerimientoResultado extends Requerimiento {
    private Integer modelo;
    private Integer trID;

    public RequerimientoResultado(Integer EcProveedora, Integer modelo, Integer trID) {
        super(EcProveedora);
        this.modelo = modelo;
        this.trID = trID;
    }

    public Integer getModelo() {
        return modelo;
    }

    public Integer getTrID() {
        return trID;
    }

    @Override
    public SubscriberMessage crearSubscripcion(Integer idSubscriptor, CreadorPedidosSubscripciones creador) {
        return creador.crearPedidoResultados(idSubscriptor, this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RequerimientoResultado other = (RequerimientoResultado) obj;
        if (this.modelo != other.modelo && (this.modelo == null || !this.modelo.equals(other.modelo))) {
            return false;
        }
        if (this.trID != other.trID && (this.trID == null || !this.trID.equals(other.trID))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.modelo != null ? this.modelo.hashCode() : 0);
        hash = 79 * hash + (this.trID != null ? this.trID.hashCode() : 0);
        return hash;
    }
    
}
