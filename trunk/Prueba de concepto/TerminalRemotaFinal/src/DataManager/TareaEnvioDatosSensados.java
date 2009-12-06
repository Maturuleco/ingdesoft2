/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManager;

import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.ObjectSet;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import Datos.DatoSensado;
import model.Mensaje;
import terminalremotafinal.Main;

/**
 *
 * @author mar
 */
public final class TareaEnvioDatosSensados extends TimerTask {

    private ObjectServer server;
    private BlockingQueue<Mensaje> salida;

    public TareaEnvioDatosSensados(ObjectServer server, BlockingQueue<Mensaje> salida) {
        this.server = server;
        this.salida = salida;
    }

    public final void run() {
        DatoSensado dato = new DatoSensado(null, null, null, null);
        ObjectContainer cliente = server.openClient();
        ObjectSet<DatoSensado> resultado;
        try {
            resultado = cliente.queryByExample(dato);
            send(resultado);
/*
            for (DatoSensado datoSensado : resultado) {
                System.out.println(datoSensado.toString());
            }
 */
            cliente.commit();
        } finally {
            cliente.close();
        }


    }

    private void send(ObjectSet<DatoSensado> datos) {
        Integer id = Main.idTR;
        Mensaje m = new Mensaje(id, Datos.DataSource.terminal_remota);
        m.setDatos(datos);
        salida.add(m);
    }
}

