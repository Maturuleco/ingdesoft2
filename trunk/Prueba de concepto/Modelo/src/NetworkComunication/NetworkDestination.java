/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package NetworkComunication;

/**
 *
 * @author tas
 */
public class NetworkDestination {
    String host;
    Integer port;

    public NetworkDestination(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

}
