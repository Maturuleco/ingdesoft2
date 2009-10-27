/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 *
 * @author Santiago Avendaño
 */
public class Regla {
    private Collection<Condicion> condiciones;
    private String mensajePrediccion;

    public Regla(Collection<Condicion> condiciones, String prediccion) {
        this.condiciones = condiciones;
        this.mensajePrediccion = prediccion;
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
