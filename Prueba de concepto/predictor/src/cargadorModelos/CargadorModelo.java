/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cargadorModelos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Modelo;

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
        File folder = new File(path);
        FileFilter ff = new FileFilter() {

            public boolean accept(File file) {
                String path = file.getPath();
                String extencion = path.split("\\.")[(path.split("\\.").length)-1];
                return file.isFile() && extencion.equalsIgnoreCase("xml");
            }
        };

        File[] files = folder.listFiles(ff);
        List<Modelo> modelos = new LinkedList<Modelo>();

        if (files != null) {
            for (int j = 0; j < files.length; j++) {
                File file = files[j];
                Modelo m = getModelo(file);
                if (m != null) {
                    modelos.add(m);
                }
            }
        }
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
            System.out.println("Modelo n:\n" + xstream.toXML(modelo));
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
}
