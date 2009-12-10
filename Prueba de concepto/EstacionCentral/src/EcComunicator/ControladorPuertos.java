/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EcComunicator;

import NetworkComunication.NetworkDestination;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author tas
 */
public class ControladorPuertos {
    Dictionary<Integer, Dictionary<TypeOfPort, Integer>> puertos = new Hashtable<Integer, Dictionary<TypeOfPort, Integer>>();
    Dictionary<Integer, String> hosts = new Hashtable<Integer, String>();
    
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
    
    public void addHost(Integer idEc, String host) {
        hosts.put(idEc, host);
    }
    
    public void addPort(Integer idEc, TypeOfPort top, Integer port ) {
        if (puertos.get(idEc) == null) {
            puertos.put(idEc, new Hashtable<TypeOfPort, Integer>());
        }
        Dictionary<TypeOfPort, Integer> value = puertos.get(idEc);
        value.put(top, port);
//        puertos.put(idEc, value);
    }
}
