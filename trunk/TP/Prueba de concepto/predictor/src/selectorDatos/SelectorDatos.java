/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package selectorDatos;

import java.io.File;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;


/**
 *
 * @author Santiago Avendaño
 */
public class SelectorDatos {


    public void escribirDatos(){
        ObjectContainer db = Db4o.openFile("/resources/dbejemplo.yap");
        db.store(1);
    }
}
