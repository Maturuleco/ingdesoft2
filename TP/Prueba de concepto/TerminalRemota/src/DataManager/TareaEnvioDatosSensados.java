/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataManager;

import TR.Initialize;
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

    public TareaEnvioDatosSensados(ObjectServer server){
        this.server = server;
    }

    public void setSalida(BlockingQueue<Mensaje> salida) {
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
        Integer id = Initialize.idTR;
        datos.subList(0, datos.size() - 1);
        Mensaje m = new Mensaje(id, model.DataSource.terminal_remota);
        m.setDatos(datos);
        salida.add(m);
    }
}

