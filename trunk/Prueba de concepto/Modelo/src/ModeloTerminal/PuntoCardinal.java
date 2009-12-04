/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ModeloTerminal;

import java.text.ParseException;

/**
 *
 * @author tas
 */
public enum PuntoCardinal {
    Norte,
    Sur,
    Este,
    Oeste;
    
    public static PuntoCardinal parse(String id) throws ParseException{
        PuntoCardinal res;
        if (id.equals("N"))
            res = PuntoCardinal.Norte;
        else if (id.equals("S"))
            res = PuntoCardinal.Sur;
        else if (id.equals("E"))
            res = PuntoCardinal.Este;
        else if (id.equals("O"))
            res = PuntoCardinal.Oeste;
        else {
            throw new ParseException(id, 0);
        }
        return res;
    }
    @Override public String toString(){
        switch(this) {
            case Norte: return "N";
            case Sur: return "S";
            case Este: return "E";
            case Oeste: return "O";
            default: 
                throw new UnsupportedOperationException("No hay entrada para" + this.name());
        }
    }
}
