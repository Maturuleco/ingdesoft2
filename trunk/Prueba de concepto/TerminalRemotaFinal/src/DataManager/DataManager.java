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
import Datos.DatoSensado;
import model.Mensaje;
import terminalremotafinal.Main;


public class DataManager extends Thread{

    private ObjectServer server;
    private BlockingQueue<DatoSensado> entrada;
    private BlockingQueue<Mensaje> salida;
    private BlockingQueue<Mensaje> respuestaEnvio;
    private static final long sleepTime = 10000;
    private final static long frequency = 10000;

    public DataManager () {
        server = Db4o.openServer("DataBuffer"+Main.idTR+".yap", 0);
    }

    public void setEntrada(BlockingQueue<DatoSensado> entrada) {
        this.entrada = entrada;
    }

    public void setSalida(BlockingQueue<Mensaje> salida) {
        this.salida = salida;
    }

    public void setRespuestaEnvio(BlockingQueue<Mensaje> respuestaEnvio) {
        this.respuestaEnvio = respuestaEnvio;
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
        Mensaje respuesta = respuestaEnvio.poll();
        if (respuesta != null) {
            eliminar(respuesta);
            return true;
        }

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
            //System.out.println("\nGUARDA\t\tEl DataManager guarda en la base: "+m.toString());
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

    private void eliminar(Mensaje respuesta) {
        //System.out.println("\nELIMINA\t\tEl Data Mannager elimina: "+respuesta.toString());
        ObjectContainer cliente = server.openClient();
        for (DatoSensado dato : respuesta.getDatos()){
            cliente.delete(dato);
            cliente.commit();
        }
        cliente.close();
    }

}
