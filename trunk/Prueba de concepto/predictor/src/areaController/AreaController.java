/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package areaController;

import java.awt.geom.Area;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Santiago Avendaño
 */
public class AreaController {

    // TODO: implementar bien. esta es la prima aproximacion
    public Collection<Integer> buscarTerminalesRemotas(Area area){
        Collection<Integer> resultado = new TreeSet<Integer>();
        resultado.add(1);
        resultado.add(2);
        resultado.add(3);
        return resultado;
    }
}
