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
    
    
    public static MensajePedidoSubscripcionDatos crearPedido(Integer idSubscriptor, RequerimientoDato req) {
        FactorClimatico factorClimatico = req.getFactor();
        Integer tr = req.getTrID();
        Integer ecProovedora = req.getProveedor();
        return new MensajePedidoSubscripcionDatos(idSubscriptor, ecProovedora, tr, factorClimatico);
    }
    public static MensajePedidoSubscripcionResultados crearPedido(Integer idSubscriptor, RequerimientoResultado req) {
        Integer idModelos = req.getModelo();
        Integer trID = req.getTrID();
        Integer ecProovedora = req.getProveedor();
        return new MensajePedidoSubscripcionResultados(idSubscriptor, ecProovedora, idModelos, trID);
    }
    public static SubscriberMessage crearPedido(Integer idSubscriptor, Requerimiento req){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
