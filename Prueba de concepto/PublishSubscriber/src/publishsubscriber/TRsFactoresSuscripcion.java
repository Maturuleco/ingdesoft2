
package publishsubscriber;

import Datos.FactorClimatico;
import model.SuscriptorMessage;

/**
 *
 * @author mar
 */
public class TRsFactoresSuscripcion extends Suscripcion{

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
    public Boolean seCorresponde(SuscriptorMessage mensaje) {
        throw new UnsupportedOperationException("Not supported yet.");
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
