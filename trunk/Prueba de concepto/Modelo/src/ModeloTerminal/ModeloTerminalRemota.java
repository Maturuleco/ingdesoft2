/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ModeloTerminal;

import java.util.Set;
import Datos.FactorClimatico;

/**
 *
 * @author tas
 */
public class ModeloTerminalRemota {
    private UbicacionGeografica localizacion;
    private Set<FactorClimatico> tipoSensores;

    public ModeloTerminalRemota(UbicacionGeografica localizacion) {
        this.localizacion = localizacion;
    }

    public void addTipoSensor(FactorClimatico tipoSensor) {
        this.tipoSensores.add(tipoSensor);
    }
    
    public void rmTipoSensor(FactorClimatico tipoSensor) {
        this.tipoSensores.remove(tipoSensor);
    }
    
}
