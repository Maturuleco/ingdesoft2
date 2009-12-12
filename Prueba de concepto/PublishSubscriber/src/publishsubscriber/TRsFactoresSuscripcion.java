
package publishsubscriber;

import Datos.DatoAlmacenado;
import Datos.FactorClimatico;
import SubscripcionesEc.MensajePedidoSubscripcionDatos;
import model.SuscriptorMessage;

/**
 *
 * @author mar
 */
public class TRsFactoresSuscripcion extends Suscripcion<DatoAlmacenado>{

    private Integer idTR;
    private FactorClimatico factor;

    public TRsFactoresSuscripcion(Integer idSuscriptor, Integer idTR, FactorClimatico factor) {
        super(idSuscriptor);
        this.idTR = idTR;
        this.factor = factor;
    }

    public FactorClimatico getFactor() {
        return factor;
    }

    public Integer getIdTR() {
        return idTR;
    }

    @Override
    public Boolean seCorresponde(DatoAlmacenado mensaje) {
        Boolean res = mensaje.getFactor().equals(getFactor());
        res = res && mensaje.getIdTR().equals(getIdTR());
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
        final TRsFactoresSuscripcion other = (TRsFactoresSuscripcion) obj;
        if (this.idTR != other.idTR && (this.idTR == null || !this.idTR.equals(other.idTR))) {
            return false;
        }
        if (this.idSuscriptor != other.idSuscriptor && (this.idSuscriptor == null || !this.idSuscriptor.equals(other.idSuscriptor))) {
            return false;
        } 
        if (this.factor != other.factor) {
            return false;
        }
        return true;
    }

}
