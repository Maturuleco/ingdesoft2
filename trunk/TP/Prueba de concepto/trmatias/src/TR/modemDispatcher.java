/*
 * Esta clase recibe los Sms, los valida y los reparte en el sistema
 */

package TR;

import java.util.concurrent.BlockingQueue;
import model.DataSource;
import model.Validador;
import red_gsm.MensajeGSM;

/**
 *
 * @author tas
 */
public class modemDispatcher {
    private static final int estacionCentral = 0;
    private BlockingQueue<MensajeGSM> modemEntrada;
    private BlockingQueue<MensajeGSM> configSalida;
    private BlockingQueue<MensajeGSM> dataSalida;
        
    public modemDispatcher() {
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

   

    private void recive(MensajeGSM sms) {
        if (sms.getOrigen() != estacionCentral) {
            String contenido = sms.getMensaje();
            if (Validador.validar(contenido, DataSource.estacion_central)) {
                String[] cuerpo = contenido.split("#");
                if ( cuerpo[0].equals("CONFIG") )
                    configSalida.add(sms);
                else if ( cuerpo[0].equals("ACK") ) {
                    dataSalida.add(sms);
                }
            }
        }
        
    }
    
}
