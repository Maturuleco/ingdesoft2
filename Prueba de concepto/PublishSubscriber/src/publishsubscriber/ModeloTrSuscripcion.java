package publishsubscriber;

import SubscripcionesEc.MensajePedidoSubscripcionResultados;
import model.SuscriptorMessage;

/**
 *
 * @author Ce
 */
public class ModeloTrSuscripcion extends Suscripcion{

    private Integer modeloID;
    private Integer trID;

    public ModeloTrSuscripcion(Integer idSuscriptor, Integer modeloID, Integer trID) {
        super(idSuscriptor);
        this.modeloID = modeloID;
        this.trID = trID;
    }
    
    //@Override
    public Boolean seCorresponde(SuscriptorMessage mensaje) {

       // TODO: modificar esta clase igual que el TRFactoresSuscripcion
       MensajePedidoSubscripcionResultados pedido = (MensajePedidoSubscripcionResultados) mensaje;

       Boolean res = pedido.getIdSuscriptor().equals(getIdSuscriptor());
       res = res && pedido.getModelo().equals(getModeloID());
       res = res && pedido.getTrID().equals(getTrID());
       return res;
    }

    public Integer getModeloID() {
        return modeloID;
    }

    public Integer getTrID() {
        return trID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModeloTrSuscripcion other = (ModeloTrSuscripcion) obj;
        if (this.modeloID != other.modeloID && (this.modeloID == null || !this.modeloID.equals(other.modeloID))) {
            return false;
        }
        if (this.trID != other.trID && (this.trID == null || !this.trID.equals(other.trID))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.modeloID != null ? this.modeloID.hashCode() : 0);
        hash = 37 * hash + (this.trID != null ? this.trID.hashCode() : 0);
        return hash;
    }

    @Override
    public Boolean seCorresponde(Object mensaje) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
