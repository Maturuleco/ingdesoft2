/*
 * Esta clase recibe los Sms, los valida y los reparte en el sistema
 */

package datareceiver;

import java.util.concurrent.BlockingQueue;
import model.DataSource;
import model.ValidatingTools;
import red_gsm.MensajeGSM;

/**
 *
 * @author Mar
 */
public class ModemDispatcher extends Thread {
    private BlockingQueue<MensajeGSM> modemEntrada;
    private BlockingQueue<MensajeGSM> startupSalida;
    private BlockingQueue<MensajeGSM> dataSalida;

    public ModemDispatcher() {
    }

    public void setStartupSalida(BlockingQueue<MensajeGSM> startupSalida) {
        this.startupSalida = startupSalida;
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
        String contenido = sms.getMensaje();
        String[] cuerpo = contenido.split("#");

        if (ValidatingTools.checkHash(cuerpo[3], cuerpo[0]+"#"+cuerpo[1]+"#"+cuerpo[2])){
           if (ValidatingTools.validar(contenido, DataSource.terminal_remota)) {
                if ( cuerpo[0].equals("Raise") )
                    startupSalida.add(sms);
                else{
                    dataSalida.add(sms);
                }
            }
        }
    }

}
