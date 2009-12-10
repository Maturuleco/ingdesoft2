/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EcComunicator;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author tas
 */
public class ParaHacerElarchdeConfigDePuertos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ControladorPuertos controlador = new ControladorPuertos();
        
        controlador.addHost(01, "localhost");
        controlador.addHost(02, "localhost");
        controlador.addHost(03, "localhost");
        controlador.addHost(04, "localhost");
        
        controlador.addPort(01, TypeClientPort.recepcion_datos,                         1030);
        controlador.addPort(01, TypeClientPort.recepcion_resultados,                    1031);
        controlador.addPort(01, TypeClientPort.recepcion_subscripcion_datos,            1032);
        controlador.addPort(01, TypeClientPort.recepcion_subscripcion_resultados,       1033);
        controlador.addPort(01, TypeServerDestinyPort.envio_datos,                      1034);
        controlador.addPort(01, TypeServerDestinyPort.envio_resultado,                  1035);
        controlador.addPort(01, TypeServerDestinyPort.envio_subscripcion_datos,         1036);
        controlador.addPort(01, TypeServerDestinyPort.envio_subscripcion_resultados,    1037);
        
        controlador.addPort(02, TypeClientPort.recepcion_datos,                         1040);
        controlador.addPort(02, TypeClientPort.recepcion_resultados,                    1041);
        controlador.addPort(02, TypeClientPort.recepcion_subscripcion_datos,            1042);
        controlador.addPort(02, TypeClientPort.recepcion_subscripcion_resultados,       1043);
        controlador.addPort(02, TypeServerDestinyPort.envio_datos,                      1044);
        controlador.addPort(02, TypeServerDestinyPort.envio_resultado,                  1045);
        controlador.addPort(02, TypeServerDestinyPort.envio_subscripcion_datos,         1046);
        controlador.addPort(02, TypeServerDestinyPort.envio_subscripcion_resultados,    1047);
        
        controlador.addPort(03, TypeClientPort.recepcion_datos,                         1050);
        controlador.addPort(03, TypeClientPort.recepcion_resultados,                    1051);
        controlador.addPort(03, TypeClientPort.recepcion_subscripcion_datos,            1052);
        controlador.addPort(03, TypeClientPort.recepcion_subscripcion_resultados,       1053);
        controlador.addPort(03, TypeServerDestinyPort.envio_datos,                      1054);
        controlador.addPort(03, TypeServerDestinyPort.envio_resultado,                  1055);
        controlador.addPort(03, TypeServerDestinyPort.envio_subscripcion_datos,         1056);
        controlador.addPort(03, TypeServerDestinyPort.envio_subscripcion_resultados,    1057);
        
        controlador.addPort(04, TypeClientPort.recepcion_datos,                         1060);
        controlador.addPort(04, TypeClientPort.recepcion_resultados,                    1061);
        controlador.addPort(04, TypeClientPort.recepcion_subscripcion_datos,            1062);
        controlador.addPort(04, TypeClientPort.recepcion_subscripcion_resultados,       1063);
        controlador.addPort(04, TypeServerDestinyPort.envio_datos,                      1064);
        controlador.addPort(04, TypeServerDestinyPort.envio_resultado,                  1065);
        controlador.addPort(04, TypeServerDestinyPort.envio_subscripcion_datos,         1066);
        controlador.addPort(04, TypeServerDestinyPort.envio_subscripcion_resultados,    1067);
        
        XStream xstream = new XStream();
        String xml1 = xstream.toXML(controlador);
        
        System.out.println("El xml del controlador\n/////////////////////////\n"+xml1+"\n/////////////////\nFin xml del controlador");
        
        
        
    }

}
