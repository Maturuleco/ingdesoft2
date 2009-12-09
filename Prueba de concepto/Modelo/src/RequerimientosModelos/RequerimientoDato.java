
package RequerimientosModelos;

import Datos.FactorClimatico;

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

}
