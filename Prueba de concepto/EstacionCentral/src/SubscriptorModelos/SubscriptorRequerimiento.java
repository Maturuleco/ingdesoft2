/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SubscriptorModelos;

import RequerimientosModelos.Requerimiento;
import SubscripcionesEc.CreadorPedidosSubscripciones;
import java.util.HashSet;
import java.util.Set;
import publishsubscriber.Subscriptor;

/**
 *
 * @author tas
 */

public class SubscriptorRequerimiento<T extends Requerimiento> extends Subscriptor {
    
    // private Set<RequerimientoResultado> requerimientosResultadosSubscriptos = new HashSet<RequerimientoResultado>();
    private Set<T> requerimientosSubscriptos = new HashSet<T>();
    
    public void procesarRequerimientos(Set<T> requerimientos) {
        for (T req : requerimientos) {
            if (! requerimientosSubscriptos.contains(req)) {
                subscribe(CreadorPedidosSubscripciones.crearPedido(estacioncentral.Main.idEc , req));
            }
        }
        
    }
    
}
