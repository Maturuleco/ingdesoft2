/*
 * Esta clase recibe los Sms, los valida y los reparte en el sistema
 */

package terminalremotafinal;

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
    private BlockingQueue<MensajeGSM> raiseSalida;
        
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

    public void setRaiseSalida(BlockingQueue<MensajeGSM> raiseSalida) {
        this.raiseSalida = raiseSalida;
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
            
            if (ValidatingTools.checkHash(cuerpo)){
               if (ValidatingTools.validar(contenido, DataSource.estacion_central)) {
                    if ( cuerpo[0].equalsIgnoreCase("CONFIG") )
                        configSalida.add(sms);
                    else if ( cuerpo[0].equalsIgnoreCase("ACK") ) {
                        dataSalida.add(sms);
                    }else if (cuerpo[0].equalsIgnoreCase("ACKUP")) {
                        raiseSalida.add(sms);
                    } else {
                        System.out.println("Tipo de mensaje desconocido: "+contenido);
                    }
                } else {
                   System.out.println("El Dispatcher rebota: "+contenido);
                }
            } else {
                System.out.println("El Dispatcher rebota por hash: "+contenido);
            }
        }        
    }
    
}
