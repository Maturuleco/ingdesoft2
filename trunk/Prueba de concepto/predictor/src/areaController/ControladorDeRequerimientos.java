/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package areaController;

import java.util.HashSet;
import java.util.Set;
import modelo.RequerimientoDato;

/**
 *
 * @author Santiago Avendaño
 */
public class ControladorDeRequerimientos {
    public Set<Integer> buscarTerminalesRemotas(Set<RequerimientoDato> reqs){
        Set<Integer> resultado = new HashSet<Integer>();
        for (RequerimientoDato req : reqs) {
            resultado.add(req.getTrID());
        }
        return resultado;
    }
}
