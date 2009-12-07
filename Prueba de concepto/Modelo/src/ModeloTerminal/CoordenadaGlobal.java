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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CoordenadaGlobal other = (CoordenadaGlobal) obj;
        if (this.puntoCardinal != other.puntoCardinal) {
            return false;
        }
        if (this.grado != other.grado && (this.grado == null || !this.grado.equals(other.grado))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.puntoCardinal != null ? this.puntoCardinal.hashCode() : 0);
        hash = 59 * hash + (this.grado != null ? this.grado.hashCode() : 0);
        return hash;
    }
    
    
}
