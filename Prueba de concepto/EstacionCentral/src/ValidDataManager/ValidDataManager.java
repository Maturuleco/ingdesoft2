package ValidDataManager;


import java.util.concurrent.BlockingQueue;
import com.db4o.ObjectServer;
import Datos.DatoAlmacenado;
import com.db4o.ObjectContainer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
/**
 *
 * @author Ce
 */
public class ValidDataManager extends Thread{
    
    private static final long sleepTime = 100;
    
    private ObjectServer serverDatosValidos;
    private BlockingQueue<DatoAlmacenado> entradaDatosInternos;

    // OJO! este mensaje de entrada (externo) que viene de un DATA SENDER 
    // no esta definido (por eso dos colas), en principio es DatoAlmacenado.
    private BlockingQueue<DatoAlmacenado> entradaDatosExternos;

    public ValidDataManager(ObjectServer server) {
         this.serverDatosValidos = server;
    }

    public void setEntradaDatosExternos(BlockingQueue<DatoAlmacenado> entradaDatosExternos) {
        this.entradaDatosExternos = entradaDatosExternos;
    }

    public void setEntradaDatosInternos(BlockingQueue<DatoAlmacenado> entradaDatosInternos) {
        this.entradaDatosInternos = entradaDatosInternos;
    }

    @Override
     public void run() {
        while (true) {
            if (! sensarEntradaDatosInternos() && !sensarEntradaDatosExternos() ) {
                try {
                    // Duermo un segundo
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }

    private boolean sensarEntradaDatosInternos() {
        DatoAlmacenado cabeza = entradaDatosInternos.poll();
        if (cabeza != null) {
            escribirDatosValidos(cabeza);
            return true;
        }
        return false;
    }
    private boolean sensarEntradaDatosExternos() {
        DatoAlmacenado cabeza = entradaDatosExternos.poll();
        if (cabeza != null) {
            escribirDatosValidos(cabeza);
            return true;
        }
        return false;
    }

     public void escribirDatosValidos(DatoAlmacenado dato) {
        ObjectContainer cliente = serverDatosValidos.openClient();
        try {
            cliente.store(dato);
            cliente.commit();
        } catch (DatabaseClosedException e) {
            System.out.println("la base que intenta ingresar se encuentra cerrada\n" + e.getMessage());
        } catch (DatabaseReadOnlyException e) {
            System.out.println("la base que intenta ingresar esta en estado read-only\n" + e.getMessage());
        } catch(Db4oIOException e){
            System.out.println("fallo la operacion de entrada salida\n" + e.getMessage());
        }finally {
            cliente.close();
        }
    }
    
}
