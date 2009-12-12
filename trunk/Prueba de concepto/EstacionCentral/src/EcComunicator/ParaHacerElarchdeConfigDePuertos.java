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
        
        controlador.addPort(01, TypeClientPort.recepcion_datos,                         4030);
        controlador.addPort(01, TypeClientPort.recepcion_resultados,                    4031);
        controlador.addPort(01, TypeClientPort.recepcion_subscripcion_datos,            4032);
        controlador.addPort(01, TypeClientPort.recepcion_subscripcion_resultados,       4033);
        controlador.addPort(01, TypeServerDestinyPort.envio_datos,                      4034);
        controlador.addPort(01, TypeServerDestinyPort.envio_resultado,                  4035);
        controlador.addPort(01, TypeServerDestinyPort.envio_subscripcion_datos,         4036);
        controlador.addPort(01, TypeServerDestinyPort.envio_subscripcion_resultados,    4037);
       
        controlador.addPort(02, TypeClientPort.recepcion_datos,                         4040);
        controlador.addPort(02, TypeClientPort.recepcion_resultados,                    4041);
        controlador.addPort(02, TypeClientPort.recepcion_subscripcion_datos,            4042);
        controlador.addPort(02, TypeClientPort.recepcion_subscripcion_resultados,       4043);
        controlador.addPort(02, TypeServerDestinyPort.envio_datos,                      4044);
        controlador.addPort(02, TypeServerDestinyPort.envio_resultado,                  4045);
        controlador.addPort(02, TypeServerDestinyPort.envio_subscripcion_datos,         4046);
        controlador.addPort(02, TypeServerDestinyPort.envio_subscripcion_resultados,    4047);
       
        controlador.addPort(03, TypeClientPort.recepcion_datos,                         4050);
        controlador.addPort(03, TypeClientPort.recepcion_resultados,                    4051);
        controlador.addPort(03, TypeClientPort.recepcion_subscripcion_datos,            4052);
        controlador.addPort(03, TypeClientPort.recepcion_subscripcion_resultados,       4053);
        controlador.addPort(03, TypeServerDestinyPort.envio_datos,                      4054);
        controlador.addPort(03, TypeServerDestinyPort.envio_resultado,                  4055);
        controlador.addPort(03, TypeServerDestinyPort.envio_subscripcion_datos,         4056);
        controlador.addPort(03, TypeServerDestinyPort.envio_subscripcion_resultados,    4057);
        
        controlador.addPort(04, TypeClientPort.recepcion_datos,                         4060);
        controlador.addPort(04, TypeClientPort.recepcion_resultados,                    4061);
        controlador.addPort(04, TypeClientPort.recepcion_subscripcion_datos,            4062);
        controlador.addPort(04, TypeClientPort.recepcion_subscripcion_resultados,       4063);
        controlador.addPort(04, TypeServerDestinyPort.envio_datos,                      4064);
        controlador.addPort(04, TypeServerDestinyPort.envio_resultado,                  4065);
        controlador.addPort(04, TypeServerDestinyPort.envio_subscripcion_datos,         4066);
        controlador.addPort(04, TypeServerDestinyPort.envio_subscripcion_resultados,    4067);
       
        XStream xstream = new XStream();
        String xml1 = xstream.toXML(controlador);
        
        System.out.println("El xml del controlador\n/////////////////////////\n"+xml1+"\n/////////////////\nFin xml del controlador");
        
        
        
    }

}
