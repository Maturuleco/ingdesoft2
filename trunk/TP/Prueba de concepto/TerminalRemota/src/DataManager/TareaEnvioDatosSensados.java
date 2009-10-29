/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataManager;

import TR.Main;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.ObjectSet;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import model.DatoSensado;
import model.Mensaje;

/**
 *
 * @author mar
 */
public final class TareaEnvioDatosSensados extends TimerTask{

    private ObjectServer server;
    private BlockingQueue<Mensaje> salida;

    public TareaEnvioDatosSensados(ObjectServer server, BlockingQueue<Mensaje> salida){
        this.server = server;
        this.salida = salida;
    }

    public final void run()
    {
        DatoSensado dato = new DatoSensado(null, null, null, null);
        ObjectContainer cliente = server.openClient();
        ObjectSet<DatoSensado> resultado;
        try {
            resultado = cliente.queryByExample(dato);
        } finally {
            cliente.close();
        }
        send(resultado);
    }

    private void send(ObjectSet<DatoSensado> datos)
    {
        Integer id = Main.idTR;
        Mensaje m = new Mensaje(id, model.DataSource.terminal_remota);
        m.setDatos(datos);
        System.out.println("El DataManager Manda: "+m.toString()+"\n");
        salida.add(m);
    }
}

