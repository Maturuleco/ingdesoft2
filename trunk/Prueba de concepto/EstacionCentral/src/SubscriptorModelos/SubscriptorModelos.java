/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SubscriptorModelos;

import java.util.concurrent.BlockingQueue;
import SubscripcionesEc.SubscriberMessage;
import SubscripcionesEc.SubscriptionAcceptedMessage;
import modelo.Modelo;

/**
 *
 * @author tas
 */
///HACERLO PARAMETRICO
public class SubscriptorModelos implements Runnable {

    private final long sleepTime = 1000;
    private volatile boolean keepTrying = true;
    private BlockingQueue<Modelo> entradaModelos;
//    private SubscriptorRequerimiento<RequerimientoDato> subscriptorDatos;
//    private SubscriptorRequerimiento<RequerimientoResultado> subscriptorResultados;
//    
//    public SubscriptorModelos() {
//        this.subscriptorDatos = new SubscriptorRequerimiento<RequerimientoDato>();
//        this.subscriptorResultados = new SubscriptorRequerimiento<RequerimientoResultado>();
//    }
    private SubscriptorRequerimiento subscriptorDatos;
    private SubscriptorRequerimiento subscriptorResultados;

    public SubscriptorModelos() {
        this.subscriptorDatos = new SubscriptorRequerimiento();
        this.subscriptorResultados = new SubscriptorRequerimiento();
    }

    public void setEntradaModelos(BlockingQueue<Modelo> entradaModelos) {
        this.entradaModelos = entradaModelos;
    }

    public void setEntradaRespuestaSubscriptor(BlockingQueue<SubscriptionAcceptedMessage> entrada) {
        subscriptorDatos.setEntradaRespuestas(entrada);
        subscriptorResultados.setEntradaRespuestas(entrada);
    }

    public void setSalidaSubscripcionesDatos(BlockingQueue<SubscriberMessage> salidaSubscripcionesDatos) {
        subscriptorDatos.setSalidaSubscripciones(salidaSubscripcionesDatos);
    }

    public void setSalidaSubscripcionesResultados(BlockingQueue<SubscriberMessage> salidaSubscripcionesResultados) {
        subscriptorResultados.setSalidaSubscripciones(salidaSubscripcionesResultados);
    }

    public void run() {

        while (keepTrying) {
            if (!sensarEntradaModelos()) {
                try {
                    // Duermo un segundo
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {
                }
            }
        }
    }

    private boolean sensarEntradaModelos() {
        Modelo cabeza = entradaModelos.poll();
        if (cabeza != null) {
            //System.out.println("[SM]RECIBI MODELO:" + cabeza.getNombreModelo());
            procesarRequerimientos(cabeza);
            return true;
        } else {
            return false;
        }
    }

    private void procesarRequerimientos(Modelo modelo) {
        subscriptorDatos.procesarRequerimientos(modelo.getRequerimientosDatos());
        subscriptorResultados.procesarRequerimientos(modelo.getRequerimientosResultados());
        //System.out.println("[SM]PROCESE REQUERIMIENTOS MODELO: " + modelo.getNombreModelo());
    }

    public void requestStop() {
        keepTrying = false;
    }
}
