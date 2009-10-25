/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictorModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
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
public class Regla {

    private Comparador comparador;
    private Comparable constante;
    private FactorClimatico factor;

    public Regla(FactorClimatico factor, Comparador comparador, Comparable constante) {
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

    public static Regla parse(String dato) throws ParseException {
        String[] partes = dato.split(" ");
        if (partes.length != 3) {
            throw new ParseException(dato, 0);
        } else {
            FactorClimatico fact = FactorClimatico.parse(partes[0]);
            Comparador comp = Comparador.parse(partes[1]);
            Float cons = Float.parseFloat(partes[2]);
            return new Regla(fact, comp, cons);
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

    public static void writeReglas(Collection<Regla> reglas, String fileName){
        try {
            FileWriter fr = new FileWriter(fileName);
            for (Regla regla : reglas) {
                regla.write(fr);
                fr.append("\n");
            }
            fr.close();
        } catch (IOException e) {
            System.out.println("IOException:");
            e.printStackTrace();
        }
    }

    public static Collection<Regla> readReglas(FileReader fr) {
        Collection<Regla> res = new LinkedList<Regla>();
        try {
            Regla regla = null;
            BufferedReader br = new BufferedReader(fr);
            String line;

            line = br.readLine();

            while (line != null) {
                regla = Regla.parse(line);
                res.add(regla);
                line = br.readLine();
            }
            br.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Regla.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(Regla.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
