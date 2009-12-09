
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
}
