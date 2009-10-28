/*
 * Esta clase recibe los Sms, los valida y los reparte en el sistema
 */

package TR;

import java.util.concurrent.BlockingQueue;
import model.DataSource;
import model.ValidatingTools;
import red_gsm.MensajeGSM;

/**
 *
 * @author tas
 */
public class ModemDispatcher extends Thread {
    private static final int estacionCentral = 0;
    private BlockingQueue<MensajeGSM> modemEntrada;
    private BlockingQueue<MensajeGSM> configSalida;
    private BlockingQueue<MensajeGSM> dataSalida;
        
    public ModemDispatcher() {
    }

    public void setConfigSalida(BlockingQueue<MensajeGSM> configSalida) {
        this.configSalida = configSalida;
    }

    public void setDataSalida(BlockingQueue<MensajeGSM> dataSalida) {
        this.dataSalida = dataSalida;
    }

    public void setModemEntrada(BlockingQueue<MensajeGSM> modemEntrada) {
        this.modemEntrada = modemEntrada;
    }

    @Override
    public void run() {
        while (true) {
            try {
                MensajeGSM respuesta = modemEntrada.take();
                recive(respuesta);
            } catch (InterruptedException ex) { }
        }
    }

   

    private void recive(MensajeGSM sms) {
        if (sms.getOrigen() != estacionCentral) {
            String contenido = sms.getMensaje();
            String[] cuerpo = contenido.split("#");
            
            if (ValidatingTools.checkHash(cuerpo[3], cuerpo[0]+"#"+cuerpo[1]+"#"+cuerpo[2])){
               if (ValidatingTools.validar(contenido, DataSource.estacion_central)) {
                    if ( cuerpo[0].equals("CONFIG") )
                        configSalida.add(sms);
                    else if ( cuerpo[0].equals("ACK") ) {
                        dataSalida.add(sms);
                    }
                }
            }            
        }        
    }
    
}
