/**
 * Created by IntelliJ IDEA.
 * User: Mar
 * Date: 25/10/2009
 * Time: 14:53:08
 * To change this template use File | Settings | File Templates.
 */

package DataManager;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import model.DatoSensado;
import model.Mensaje;


public class DataManager extends Thread{

    private ObjectServer server;
    private BlockingQueue<DatoSensado> entrada;
    private BlockingQueue<Mensaje> salida;
    private static final long sleepTime = 100;
    private final static long frequency = 1000;

    public DataManager () {
        server = Db4o.openServer("DataBuffer.yap", 0);
    }

    public void setEntrada(BlockingQueue<DatoSensado> entrada) {
        this.entrada = entrada;
    }

    public void setSalida(BlockingQueue<Mensaje> salida) {
        this.salida = salida;
    }



    @Override
    public void run() {
        Timer timer = new Timer();
        TimerTask tareaEnvio = new TareaEnvioDatosSensados(server, salida);

        timer.scheduleAtFixedRate(tareaEnvio, new Date(), frequency);

        while (true) {
            if (! sensarEntradaDatos() ) {
                try {
                    // Duermo un segundo
                    sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }

    private boolean sensarEntradaDatos() {
        DatoSensado cabeza = entrada.poll();
        if (cabeza != null) {
            guardar(cabeza);
            return true;
        }
        return false;
    }

    public void guardar(DatoSensado m) {
        ObjectContainer cliente = server.openClient();
        try {
            cliente.store(m);
            cliente.commit();
        } catch (DatabaseClosedException e) {
            System.out.println("la base que intenta ingresar se encuentra cerrada");
            System.out.println(e.getMessage());
        } catch (DatabaseReadOnlyException e) {
            System.out.println("la base que intenta ingresar esta en estado read-only");
            System.out.println(e.getMessage());
        } finally {
            cliente.close();
        }
    }

}
