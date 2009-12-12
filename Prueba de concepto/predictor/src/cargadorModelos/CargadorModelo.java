/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cargadorModelos;

import Datos.FactorClimatico;
import RequerimientosModelos.RequerimientoDato;
import RequerimientosModelos.RequerimientoResultado;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Comparador;
import modelo.Condicion;
import modelo.Modelo;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño, Ce y Mat
 */
public class CargadorModelo {

//    public static void main(String[] args) {
//        CargadorModelo cargador = new CargadorModelo("../Configuraciones/ModelosEc1");
//        cargador.getModelos();
//    }
    
    private String path;

    public CargadorModelo(String path) {
        this.path = path;
    }

    public Collection<Modelo> getModelos() {
        Collection<Modelo> modelos = new LinkedList<Modelo>();

        Modelo modelo1 = new Modelo(1, getReglas1());
        Modelo modelo2 = new Modelo(2, getReglas2());

        modelo1.setRequerimientosDatos(getReqDato1());
        modelo1.setRequerimientosResultados(new HashSet<RequerimientoResultado>());
        modelo2.setRequerimientosDatos(new HashSet<RequerimientoDato>());
        modelo2.setRequerimientosResultados(getReqResult2());

        modelos.add(modelo1);
        modelos.add(modelo2);
//        File folder = new File(path);
//        FileFilter ff = new FileFilter() {
//
//            public boolean accept(File file) {
//                String path = file.getPath();
//                String extencion = path.split("\\.")[(path.split("\\.").length)-1];
//                return file.isFile() && extencion.equalsIgnoreCase("xml");
//            }
//        };
//
//        File[] files = folder.listFiles(ff);
//        List<Modelo> modelos = new LinkedList<Modelo>();
//
//        if (files != null) {
//            for (int j = 0; j < files.length; j++) {
//                File file = files[j];
//                Modelo m = getModelo(file);
//                System.out.println(m.toString());
//                if (m != null) {
//                    modelos.add(m);
//                }
//            }
//        }
        return modelos;
    }



    private Modelo getModelo(File file) {
        FileInputStream fis = null;
        Modelo modelo = null;
        try {
            while (!file.canRead()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CargadorModelo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            file.setReadOnly();
            XStream xstream = new XStream();
            fis = new FileInputStream(file);

            modelo = (Modelo) xstream.fromXML(fis);
            file.setWritable(true);
//            System.out.println("Modelo n:\n" + xstream.toXML(modelo));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CargadorModelo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CargadorModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return modelo;
    }

    private static Set<RequerimientoDato> getReqDato1() {
        Set<RequerimientoDato> reqs = new HashSet<RequerimientoDato>();
        reqs.add(new RequerimientoDato(02, 01, FactorClimatico.lluvias));
        reqs.add(new RequerimientoDato(02, 02, FactorClimatico.lluvias));
        reqs.add(new RequerimientoDato(02, 03, FactorClimatico.temperatura));
        reqs.add(new RequerimientoDato(02, 04, FactorClimatico.humedad));
        return reqs;
    }

    private static Set<RequerimientoResultado> getReqResult2() {
        Set<RequerimientoResultado> reqs = new HashSet<RequerimientoResultado>();
        reqs.add(new RequerimientoResultado(02, 69, 01));
        reqs.add(new RequerimientoResultado(02, 69, 02));
        reqs.add(new RequerimientoResultado(02, 7070, 04));
        return reqs;
    }

    private static Collection<Regla> getReglas1() {
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

    private static Collection<Regla> getReglas2() {
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
}
