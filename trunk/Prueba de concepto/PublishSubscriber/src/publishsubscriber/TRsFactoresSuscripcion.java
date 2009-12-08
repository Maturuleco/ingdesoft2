
package publishsubscriber;

import java.util.List;
import Datos.FactorClimatico;
import model.SuscriptorMessage;

/**
 *
 * @author mar
 */
public class TRsFactoresSuscripcion extends Suscripcion{

    private List<Integer> idsTR;
    private List<FactorClimatico> factores;

    public List<FactorClimatico> getFactores() {
        return factores;
    }

    public void setFactores(List<FactorClimatico> factores) {
        this.factores = factores;
    }

    public List<Integer> getIdsTR() {
        return idsTR;
    }

    public void setIdsTR(List<Integer> idsTR) {
        this.idsTR = idsTR;
    }

    @Override
    public Boolean seCorresponde(SuscriptorMessage mensaje) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
