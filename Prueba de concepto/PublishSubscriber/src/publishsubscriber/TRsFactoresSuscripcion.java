/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package publishsubscriber;

import java.util.List;
import Datos.FactorClimatico;

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

    public Integer getIdEC() {
        return idEC;
    }

    public void setIdEC(Integer idEC) {
        this.idEC = idEC;
    }

    public List<Integer> getIdsTR() {
        return idsTR;
    }

    public void setIdsTR(List<Integer> idsTR) {
        this.idsTR = idsTR;
    }

    Integer idEC;
    List<Integer> idsTR;
    List<FactorClimatico> factores;
}
