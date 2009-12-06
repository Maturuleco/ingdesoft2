/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import java.text.ParseException;

/**
 *
 * @author Santiago Avendaño
 */
public enum DataSource {
    terminal_remota,
    biggest_satellite,
    triangulador,
    sistema_eolico,
    estacion_central;

    public static DataSource parse(String id) throws ParseException{
        DataSource res;
        if (id.equals("T"))
            res = DataSource.terminal_remota;
        else if (id.equals("B"))
            res = DataSource.biggest_satellite;
        else if (id.equals("V"))
            res = DataSource.triangulador;
        else if (id.equals("E"))
            res = DataSource.sistema_eolico;
        else if (id.equals("C"))
            res = DataSource.estacion_central;
        else
            throw new ParseException(id, 0);
        return res;
    }

    @Override public String toString(){
        switch(this) {
            case terminal_remota: return "T";
            case biggest_satellite: return "B";
            case triangulador: return "V";
            case sistema_eolico: return "E";
            case estacion_central: return "C";
            default: return "ERROR";
        }
    }
}
