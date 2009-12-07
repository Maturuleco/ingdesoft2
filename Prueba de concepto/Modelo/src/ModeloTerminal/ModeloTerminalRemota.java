/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ModeloTerminal;

import java.util.Set;
import Datos.FactorClimatico;
import java.text.ParseException;
import java.util.HashSet;

/**
 *
 * @author tas
 */

public class ModeloTerminalRemota {
    private int trID;
    private UbicacionGeografica position;
    private Set<FactorClimatico> tipoSensores;

    public ModeloTerminalRemota(int id, UbicacionGeografica localizacion) {
        this.trID = id;
        this.position = localizacion;
        this.tipoSensores = new HashSet<FactorClimatico>();
    }

    public int getTrID() {
        return trID;
    }

    public UbicacionGeografica getPosition() {
        return position;
    }

    public Set<FactorClimatico> getTipoSensores() {
        return tipoSensores;
    }

    public void addTipoSensor(FactorClimatico tipoSensor) {
        this.tipoSensores.add(tipoSensor);
    }
    
    public void rmTipoSensor(FactorClimatico tipoSensor) {
        this.tipoSensores.remove(tipoSensor);
    }
    
    @Override
    public String toString() {
        String salida ="";
        salida += "trID:"+trID+"#";
        salida += "pos:"+position.toString()+"#";
        salida += "tipoSensores:";
        for (FactorClimatico factorClimatico : tipoSensores) {
            salida += factorClimatico.toString()+"|";
        }
        if (!tipoSensores.isEmpty())
            salida = salida.substring(0, salida.length()-1);
        return salida;
    }
    
    private static String getDataFromLine(String linea) throws ParseException {
        if (linea == null) {
            throw new ParseException(linea, 0);
        }
        String[] partes = linea.split(":");
        if (partes.length != 1 && partes.length != 2) {
            throw new ParseException(linea, 0);
        }
        String res = "";
        if (partes.length == 2)
            res = partes[1];
        return res;
    }
    
    public static ModeloTerminalRemota parse(String entrada) throws ParseException{
        ModeloTerminalRemota res = null;
        String[] partes = entrada.split("#");

        if (partes.length != 3)
            throw new ParseException(entrada, 0);
        else{
            int trID = (int) Integer.valueOf(getDataFromLine(partes[0]));
            UbicacionGeografica ug = UbicacionGeografica.parse(getDataFromLine(partes[1]));
            res = new ModeloTerminalRemota(trID, ug);
            String sensores = getDataFromLine(partes[2]);
            if (!sensores.isEmpty()) {
                String[] tiposSensores = sensores.split("\\|");
                for (String sensorStr : tiposSensores) {
                    FactorClimatico fc = FactorClimatico.parse(sensorStr);
                    res.addTipoSensor(fc);
                }
            }
        }
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
        final ModeloTerminalRemota other = (ModeloTerminalRemota) obj;
        if (this.trID != other.trID) {
            return false;
        }
        if (this.position != other.position && (this.position == null || !this.position.equals(other.position))) {
            return false;
        }
        if (this.tipoSensores != other.tipoSensores && (this.tipoSensores == null || !this.tipoSensores.equals(other.tipoSensores))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.trID;
        hash = 59 * hash + (this.position != null ? this.position.hashCode() : 0);
        hash = 59 * hash + (this.tipoSensores != null ? this.tipoSensores.hashCode() : 0);
        return hash;
    }
    
    
    
}
