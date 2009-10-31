/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Map;
import model.FactorClimatico;

/**
 *
 * @author Santiago Avendaño
 */
public class Regla {
    private Collection<Condicion> condiciones;
    private String mensajePrediccion;
    private Map<FactorClimatico, Collection<Condicion>> condicionesPorFactor;

    public Regla(Collection<Condicion> condiciones, String prediccion) {
        this.condiciones = condiciones;
        this.mensajePrediccion = prediccion;
        this.condicionesPorFactor = ordenarCondicionesPorFactor();
    }

    public Collection<Condicion> getCondiciones() {
        return condiciones;
    }

    public void setCondiciones(Collection<Condicion> condiciones) {
        this.condiciones = condiciones;
    }

    public String getMensajePrediccion() {
        return mensajePrediccion;
    }

    public void setMensajePrediccion(String prediccion) {
        this.mensajePrediccion = prediccion;
    }

    public Map<FactorClimatico, Collection<Condicion>> condicionesPorFactor(){
        return condicionesPorFactor;
    }

    private Map<FactorClimatico, Collection<Condicion>> ordenarCondicionesPorFactor() {

        Map<FactorClimatico, Collection<Condicion>> result = new EnumMap<FactorClimatico, Collection<Condicion>>(FactorClimatico.class);

        for (FactorClimatico factor : FactorClimatico.values()) {
            result.put(factor, new LinkedList<Condicion>());
        }

        for (Condicion condicion : condiciones) {
            result.get(condicion.getFactor()).add(condicion);
        }

        return result;
    }
    
    public void writeRegla(String fileName){
        try {
            FileWriter fr = new FileWriter(fileName);
            for (Condicion condicion : condiciones) {
                fr.append("c:");
                condicion.write(fr);
                fr.append("\n");
            }
            fr.append("p:");
            fr.append(mensajePrediccion);
            fr.close();
        } catch (IOException e) {
            System.out.println("IOException:");
            e.printStackTrace();
        }
    }
}
