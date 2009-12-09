/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SubscripcionesEc;

import Datos.FactorClimatico;
import RequerimientosModelos.*;


/**
 *
 * @author tas
 */
public class CreadorPedidosSubscripciones {
    
    
    public MensajePedidoSubscripcionDatos crearPedidoDatos(Integer idSubscriptor, RequerimientoDato req) {
        FactorClimatico factorClimatico = req.getFactor();
        Integer tr = req.getTrID();
        Integer ecProovedora = req.getProveedor();
        return new MensajePedidoSubscripcionDatos(idSubscriptor, ecProovedora, tr, factorClimatico);
    }
    public MensajePedidoSubscripcionResultados crearPedidoResultados(Integer idSubscriptor, RequerimientoResultado req) {
        Integer idModelos = req.getModelo();
        Integer trID = req.getTrID();
        Integer ecProovedora = req.getProveedor();
        return new MensajePedidoSubscripcionResultados(idSubscriptor, ecProovedora, idModelos, trID);
    }
    
//    public SubscriberMessage crearPedido(Integer idSubscriptor, Requerimiento req){
//        return req.crearSubscripcion(idSubscriptor, this);
//    }
    
}
