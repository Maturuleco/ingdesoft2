/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.text.ParseException;

/**
 *
 * @author Santiago Avendaño
 */
public enum Comparador {
    menor,
    mayor,
    igual,
    mayeq,
    meneq;

    public static Comparador parse(String id) throws ParseException{
        Comparador res;
        if (id.equals("<"))
            res = Comparador.menor;
        else if (id.equals(">"))
            res = Comparador.mayor;
        else if (id.equals("=="))
            res = Comparador.igual;
        else if (id.equals("<="))
            res = Comparador.meneq;
        else if (id.equals(">="))
            res = Comparador.mayeq;
        else
            throw new ParseException(id, 0);

        return res;
    }

    @Override public String toString(){
        switch(this) {
            case menor: return "<";
            case mayor: return ">";
            case igual: return "==";
            case mayeq: return ">=";
            case meneq: return "<=";
            default:
                throw new UnsupportedOperationException("No hay entrada para" + this.name());
        }
    }
    
}
