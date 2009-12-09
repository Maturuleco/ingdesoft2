/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SubscriptorModelos;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import model.MensajePedidoSubscripcionDatos;
import model.MensajePedidoSubscripcionResultados;
import SubscripcionesEc.SubscriberMessage;
import model.SuscriptorMessage;
import modelo.Modelo;
import modelo.Requerimiento;
import modelo.RequerimientoDato;
import modelo.RequerimientoResultado;
import publishsubscriber.Subscriptor;

/**
 *
 * @author tas
 */

///HACERLO PARAMETRICO
public class SubscriptorRequerimientos implements Runnable{
    private final long sleepTime = 1000;
    private BlockingQueue<Modelo> entradaModelos;
    private BlockingQueue<SuscriptorMessage> entradaRespuestaSubscriptor;
    private BlockingQueue<SubscriberMessage> salidaSubscripcionesDatos;
    private BlockingQueue<SubscriberMessage> salidaSubscripcionesResultados;
    
    private Set<Requerimiento> requerimientosSubscriptos = new HashSet();
    private Set<RequerimientoResultado> requerimientosResultadosSubscriptos = new HashSet<RequerimientoResultado>();
    
    //TODO Un diccionario de Pedido -> Requerimiento

    public void run() {
        
        while (true) {
            if (! sensarEntradaModelos() ) {
                try {
                    // Duermo un segundo
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }

    private boolean sensarEntradaModelos() {
        Modelo cabeza = entradaModelos.poll();
        if (cabeza != null) {
            procesarRequerimientos(cabeza);
            return true;
        }
        return false;
    }
    
    private boolean sensarEntradaRespuestaSubscriptor() {
        SuscriptorMessage cabeza = entradaRespuestaSubscriptor.poll();
        if (cabeza != null) {
            procesarRespuesta(cabeza);
            return true;
        }
        return false;
    }

    private void procesarRespuesta(SuscriptorMessage respuesta) {
        if (respuesta.subscripcionAceptada()) {
            respuesta
        }
    }
    
    private void procesarRequerimientos(Modelo modelo) {
        procesarRequerimientosDatos(modelo.getRequerimientosDatos());
        procesarRequerimientosResultados(modelo.getRequerimientosResultados());
    }
    
    
    private void procesarRequerimientosDatos(Set<RequerimientoDato> requerimientosDatos) {
        for (RequerimientoDato req : requerimientosDatos) {
            if (! requerimientosDatosSubscriptos.contains(req)) {
                subscribirRequerimiento(req);
                //TODO: Ojo que se asume que el mensaje no se pierde!!!
            }
        }
    }

    private void procesarRequerimientosResultados(Set<RequerimientoResultado> requerimientosResultados) {
        for (RequerimientoResultado req : requerimientosResultados) {
            if (! requerimientosResultadosSubscriptos.contains(req)) {
                subscribirRequerimiento(req);
                //TODO: Ojo que se asume que el mensaje no se pierde!!!
            }
        }
    }

    private void subscribirRequerimiento(RequerimientoDato req) {
        
    }
    
    private void subscribirRequerimiento(RequerimientoResultado req) {
        
    }
    
}
