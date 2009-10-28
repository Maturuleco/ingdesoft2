/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package selectorDatos;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import java.util.Collection;
import java.util.List;
import model.DatoAlmacenado;

/**
 *
 * @author Santiago Avendaño
 */
public class SelectorDatos {

    public static final String nombrebd = "resoruces/validData.yap";

    public void escribirDatos(Collection<DatoAlmacenado> datos) {
        ObjectContainer db = Db4o.openFile(nombrebd);
        try {
            for (DatoAlmacenado datoAlmacenado : datos) {
                db.store(datoAlmacenado);
            }

        } catch(DatabaseClosedException e){
            System.out.println("la base que intenta ingresar se encuentra cerrada");
            System.out.println(e.getMessage());
        }catch(DatabaseReadOnlyException e){
            System.out.println("la base que intenta ingresar esta en estado read-only");
            System.out.println(e.getMessage());
        }finally {
            db.close();
        }

    }

    public List<DatoAlmacenado> leerTodosLosDatos() {
        DatoAlmacenado prototipo = new DatoAlmacenado(null, null, null,null, null, null);
        ObjectContainer db = Db4o.openFile(nombrebd);
        ObjectSet<DatoAlmacenado> resultado;
        try {
            resultado = db.queryByExample(prototipo);

        } finally {
            db.close();
        }
        return resultado.subList(0, resultado.size()-1);
    }
}
