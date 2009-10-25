/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.text.ParseException;

/**
 *
 * @author Santiago Avendaño
 */
public enum FactorClimatico {
    temperatura,
    humedad,
    presion,
    lluvias,
    velocidad_viento,
    direccion_viento;

    public static FactorClimatico parse(String id) throws ParseException{
        FactorClimatico res;
        if (id.equals("T"))
            res = FactorClimatico.temperatura;
        else if (id.equals("H"))
            res = FactorClimatico.humedad;
        else if (id.equals("P"))
            res = FactorClimatico.presion;
        else if (id.equals("L"))
            res = FactorClimatico.lluvias;
        else if (id.equals("V"))
            res = FactorClimatico.velocidad_viento;
        else if (id.equals("D"))
            res = FactorClimatico.direccion_viento;
        else
            throw new ParseException(id, 0);
        return res;
    }

    @Override public String toString(){
        switch(this) {
            case temperatura: return "T";
            case humedad: return "H";
            case presion: return "P";
            case lluvias: return "L";
            case velocidad_viento: return "V";
            case direccion_viento: return "D";
            default: return "ERROR";
        }
    }

}
