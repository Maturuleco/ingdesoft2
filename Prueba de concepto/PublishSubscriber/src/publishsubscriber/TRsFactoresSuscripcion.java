/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package publishsubscriber;

import java.util.List;
import Datos.FactorClimatico;
import model.SuscriptorMessage;

/**
 *
 * @author mar
 */
public class TRsFactoresSuscripcion extends Suscripcion{

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

    List<Integer> idsTR;
    List<FactorClimatico> factores;

    @Override
    public Boolean seCorresponde(SuscriptorMessage mensaje) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
