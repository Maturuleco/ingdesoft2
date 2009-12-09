/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EcComunicator;

import NetworkComunication.NetworkDestination;
import java.util.Dictionary;

/**
 *
 * @author tas
 */
public class ControladorPuertos {
    Dictionary<Integer, Dictionary<TypeOfPort, Integer>> puertos;
    Dictionary<Integer, String> hosts;
    
    public NetworkDestination getNetworkDestination(Integer ec, TypeServerDestinyPort tipoPuerto) {
        Integer port = null;
        NetworkDestination res = null;
        Dictionary<TypeOfPort, Integer> subDic = puertos.get(ec);
        String host = hosts.get(ec);
        if (host != null){
            port = getPort(ec, tipoPuerto);
            if (port != null)
                res = new NetworkDestination(host, port);
        }
        return res;
    }
    
    public Integer getPort(Integer ec, TypeServerDestinyPort tipoPuerto) {
        Dictionary<TypeOfPort, Integer> puertosEc = puertos.get(ec);
        Integer port = null;
        if (puertosEc != null)
            port = puertosEc.get(tipoPuerto);
       return port;
    }
}
