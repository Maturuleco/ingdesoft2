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
public class CoordenadaGlobal {
    private PuntoCardinal puntoCardinal;
    private Integer grado;

    public Integer getGrado() {
        return grado;
    }

    public PuntoCardinal getPuntoCardinal() {
        return puntoCardinal;
    }

    public CoordenadaGlobal(PuntoCardinal puntoCardinal, Integer grado) {
        this.puntoCardinal = puntoCardinal;
        this.grado = grado;
    }
    
    public static CoordenadaGlobal parse(String datos) throws ParseException {
        String[] elementos = datos.split("\\_");
        Integer gr;
        PuntoCardinal pc;
        if (elementos.length != 2)
            throw new ParseException(datos, 0);
        else {
            gr = Integer.valueOf(elementos[0]);
            pc = PuntoCardinal.parse(elementos[1]);
        }
        return new CoordenadaGlobal(pc, gr);
    }
    @Override public String toString(){
        return grado.toString() + '_' + puntoCardinal.toString();
    }
}
