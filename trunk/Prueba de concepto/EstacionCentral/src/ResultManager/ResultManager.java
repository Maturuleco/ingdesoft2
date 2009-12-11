package ResultManager;

import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import evaluador.ResultadoEvaluacion;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Ce
 */
public class ResultManager extends Thread {

    private static final long sleepTime = 100;

    private ObjectServer serverResultadosParciales;
    private ObjectServer serverPredicciones;

    private BlockingQueue<Collection<ResultadoEvaluacion>> entradaResultadosParciales;
    
    // TODO: esta cola deberia ser de predicciones.. pero aun no esta definido el tipo.
    private BlockingQueue<Collection<ResultadoEvaluacion>> entradaPredicciones;

    public ResultManager(ObjectServer serverResultadosParciales, ObjectServer serverPredicciones) {
        this.serverResultadosParciales = serverResultadosParciales;
        this.serverPredicciones = serverPredicciones;
    }

    @Override
     public void run() {
        while (true) {
            // La cola de entrada de resultados parciales tiene mas prioridad..
            if (! sensarEntradaResultadosParciales() ) {
                try {
                    sensarEntradaPredicciones();
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }

    private boolean sensarEntradaResultadosParciales() {
        Collection<ResultadoEvaluacion> cabeza = entradaResultadosParciales.poll();
        if (cabeza != null) {
            escribirResultadosParciales(cabeza);
            return true;
        }
        return false;
    }

    private boolean sensarEntradaPredicciones() {
        Collection<ResultadoEvaluacion> cabeza = entradaPredicciones.poll();
        if (cabeza != null) {
            escribirResultadosParciales(cabeza);
            return true;
        }
        return false;
    }

    // Estos dos metodos podrian ser mas simples, pero recien despues de que se defina el tipo de mensaje de prediccion
     public void escribirResultadosParciales(Collection<ResultadoEvaluacion> datos) {
        ObjectContainer cliente = serverResultadosParciales.openClient();
        try {
            for (ResultadoEvaluacion resultadoEvaluacion : datos) {
                cliente.store(resultadoEvaluacion);
                cliente.commit();
            }
            
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

    public void escribirPredicciones(Collection<ResultadoEvaluacion> datos) {
        ObjectContainer cliente = serverPredicciones.openClient();
        try {
            for (ResultadoEvaluacion resultadoEvaluacion : datos) {
                cliente.store(resultadoEvaluacion);
                cliente.commit();
            }

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

    public void setServerPredicciones(ObjectServer serverPredicciones) {
        this.serverPredicciones = serverPredicciones;
    }

    public void setServerResultadosParciales(ObjectServer serverResultadosParciales) {
        this.serverResultadosParciales = serverResultadosParciales;
    }
    
    public void setEntradaPredicciones(BlockingQueue<Collection<ResultadoEvaluacion>> entradaPredicciones) {
        this.entradaPredicciones = entradaPredicciones;
    }

    public void setEntradaResultadosParciales(BlockingQueue<Collection<ResultadoEvaluacion>> entradaResultadosParciales) {
        this.entradaResultadosParciales = entradaResultadosParciales;
    }

}
