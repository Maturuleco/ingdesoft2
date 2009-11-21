/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DatoSensado;
import model.FactorClimatico;

/**
 *
 * @author Santiago Avendaño
 */
public class Condicion {

    private Comparador comparador;
    private Comparable constante;
    private FactorClimatico factor;

    public Condicion(FactorClimatico factor, Comparador comparador, Comparable constante) {
        this.comparador = comparador;
        this.constante = constante;
        this.factor = factor;
    }

    public Comparador getComparador() {
        return comparador;
    }

    public Comparable getConstante() {
        return constante;
    }

    public FactorClimatico getFactor() {
        return factor;
    }

    public boolean aplicar(DatoSensado dato) {
        boolean res = true;

        if (dato.getFactor().equals(factor)) {
            switch (comparador) {
                case igual:
                    return constante.compareTo(dato.getValor()) == 0;
                case menor:
                    return constante.compareTo(dato.getValor()) > 0;
                case mayor:
                    return constante.compareTo(dato.getValor()) < 0;
                case mayeq:
                    return (constante.compareTo(dato.getValor()) < 0) || (constante.compareTo(dato.getValor()) == 0);
                case meneq:
                    return (constante.compareTo(dato.getValor()) > 0) || (constante.compareTo(dato.getValor()) == 0);
                default:
                    throw new UnsupportedOperationException("El operador " + comparador + "no esá soportado");
            }
        }
        return res;
    }

    public static Condicion parse(String dato) throws ParseException {
        String[] partes = dato.split(" ");
        if (partes.length != 3) {
            throw new ParseException(dato, 0);
        } else {
            FactorClimatico fact = FactorClimatico.parse(partes[0]);
            Comparador comp = Comparador.parse(partes[1]);
            Float cons = Float.parseFloat(partes[2]);
            return new Condicion(fact, comp, cons);
        }
    }

    @Override
    public String toString() {
        String fact = factor.toString();
        String comp = comparador.toString();
        String cons = constante.toString();

        return fact + " " + comp + " " + cons;
    }

    public void write(FileWriter fw) throws IOException {
        fw.append(this.toString());
    }

    public static void writeCondiciones(Collection<Condicion> condiciones, String fileName){
        try {
            FileWriter fr = new FileWriter(fileName);
            for (Condicion condicion : condiciones) {
                condicion.write(fr);
                fr.append("\n");
            }
            fr.close();
        } catch (IOException e) {
            System.out.println("IOException:");
            e.printStackTrace();
        }
    }

    public static Collection<Condicion> readCondiciones(FileReader fr) {
        Collection<Condicion> res = new LinkedList<Condicion>();
        try {
            Condicion condicion = null;
            BufferedReader br = new BufferedReader(fr);
            String line;

            line = br.readLine();

            while (line != null) {
                condicion = Condicion.parse(line);
                res.add(condicion);
                line = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Condicion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Condicion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
