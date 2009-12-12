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
//public class SubscriptorRequerimiento extends Subscriptor {
    
     private Set<T> requerimientosSubscriptos = new HashSet<T>();
  //  private Set<Requerimiento> requerimientosSubscriptos = new HashSet<Requerimiento>();
    
    //public void procesarRequerimientos(Set<? extends Requerimiento> requerimientos) {
     public void procesarRequerimientos(Set<T> requerimientos) {
        for (T req : requerimientos) {
            if (! requerimientosSubscriptos.contains(req)) {
                CreadorPedidosSubscripciones cp = new CreadorPedidosSubscripciones();
                System.out.println("[SR]Me suscribo");
                subscribe(req.crearSubscripcion(estacioncentral.Main.idEc , cp));
            }
        }
        
    }
    
}
