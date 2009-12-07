/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cargadorModelos;

import java.util.Collection;
import java.util.LinkedList;
import Datos.FactorClimatico;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Comparador;
import modelo.Condicion;
import modelo.Modelo;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño
 */
public class CargadorModelo {

    private String path = "MODELOS/";
    private List<Modelo> modelos = new LinkedList<Modelo>();

    public CargadorModelo(){
        File folder = new File (path);
        File[] files = folder.listFiles();

        if (files != null) {
            for (int j = 0; j < files.length; j++) {
                File file = files[j];
                cargarModelo(file);
            }
        }
    }

    public Collection<Modelo> getModelosCargados() {
        return modelos;
    }

    private void cargarModelo(File file) {
        int numRegla = 0;
        FileReader fr = null;
        String nombreModelo = null;
        Collection<Regla> reglas = new LinkedList<Regla>();

        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            
            //Ej. Nombre Modelo: FRIO
            String linea = br.readLine();
            nombreModelo = getNombreModelo(linea);
            
            // Condiciones del modelo
            linea = br.readLine();
            if( linea == null)
                throw new ParseException("ERROR. El modelo no contiene condiciones.", 0);

            while (linea != null) {
                Collection<Condicion> condiciones = new LinkedList<Condicion>();
                condiciones.add(getCondicion(linea));
                reglas.add(new Regla("Regla " + numRegla, condiciones));

                linea = br.readLine();
            }
            fr.close();
            Modelo modelo = new Modelo(nombreModelo, reglas, null);
            modelos.add(modelo);

        } catch (Exception ex) {
            Logger.getLogger(CargadorModelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getNombreModelo(String linea) throws ParseException {
        if (linea == null) {
            throw new ParseException("ERROR. El archivo de modelos esta vacio", 0);
        }
        String[] partes = linea.split(":");
        if (partes.length != 2) {
            throw new ParseException("ERROR. El archivo de modelos no esta bien formado." + linea, 0);
        }
        return partes[1];
    }

    private Condicion getCondicion(String linea) throws ParseException {
        if (linea == null) {
            throw new ParseException("ERROR. El archivo de modelos esta vacio", 0);
        }
        String[] partes = linea.split(";");
        if (partes.length != 3) {
            throw new ParseException("ERROR. El archivo de modelos no esta bien formado." + linea, 0);
        }
        
        FactorClimatico factorClimatico = FactorClimatico.parse(partes[0]);
        Comparador comparador = Comparador.parse(partes[1]);
        String valor = partes[2];

        Condicion condicion = new Condicion(factorClimatico, comparador, valor);

        return condicion;
    }

    // --- SACAR DESPUES DE PROBAR LO DE ARRIBA
    public Collection<Modelo> getModelos() {
        Collection<Modelo> modelos = new LinkedList<Modelo>();
        Area area1 = new Area(new Rectangle(0, 0, 10, 10));
        Modelo modelo1 = new Modelo("FRIO", getReglas1(), area1);
        //modelos.add(modelo1);
        Modelo modelo2 = new Modelo("FRIO POLAR", getReglas2(), area1);
        modelos.add(modelo2);
        return modelos;
    }

    private Collection<Regla> getReglas1() {
        Collection<Regla> reglas = new LinkedList<Regla>();
        Collection<Condicion> condiciones1 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones2 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones3 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones4 = new LinkedList<Condicion>();
        Collection<Condicion> condiciones5 = new LinkedList<Condicion>();
        condiciones1.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, 0.0f));
        condiciones1.add(new Condicion(FactorClimatico.presion, Comparador.mayor, 1020.0f));
        condiciones1.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, -10.0f));
        condiciones2.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, -10.0f));
        condiciones3.add(new Condicion(FactorClimatico.humedad, Comparador.menor, 50.0f));
        condiciones4.add(new Condicion(FactorClimatico.velocidad_viento, Comparador.mayor, 40.0f));
        condiciones4.add(new Condicion(FactorClimatico.direccion_viento, Comparador.igual, 0.0f));
        condiciones5.add(new Condicion(FactorClimatico.lluvias, Comparador.mayor, 30.0f));
        reglas.add(new Regla("Regla 1", condiciones1));
        reglas.add(new Regla("Regla 2", condiciones2));
        reglas.add(new Regla("Regla 3", condiciones3));
        reglas.add(new Regla("Regla 4", condiciones4));
        reglas.add(new Regla("Regla 5", condiciones5));

        return reglas;
    }

    private Collection<Regla> getReglas2() {
        Collection<Regla> reglas = new LinkedList<Regla>();
        Collection<Condicion> condicionesTemperatura = new LinkedList<Condicion>();
        Collection<Condicion> condicionesHumedad = new LinkedList<Condicion>();
        condicionesTemperatura.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, 0.0f));
        condicionesTemperatura.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, -10.0f));
        condicionesTemperatura.add(new Condicion(FactorClimatico.temperatura, Comparador.menor, -20.0f));
        condicionesHumedad.add(new Condicion(FactorClimatico.humedad, Comparador.menor, 60.0f));
        condicionesHumedad.add(new Condicion(FactorClimatico.humedad, Comparador.menor, 50.0f));
        condicionesHumedad.add(new Condicion(FactorClimatico.humedad, Comparador.menor, 40.0f));

        reglas.add(new Regla("Regla 1", condicionesTemperatura));
        reglas.add(new Regla("Regla 2", condicionesHumedad));

        return reglas;
    }
 // --- END SACAR
}
