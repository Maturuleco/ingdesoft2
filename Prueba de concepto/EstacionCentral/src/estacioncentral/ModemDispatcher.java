/*
 * Esta clase recibe los Sms, los valida y los reparte en el sistema
 */
package estacioncentral;

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
            } catch (InterruptedException ex) {
            }
        }
    }

    private void recive(MensajeGSM sms) {
        String contenido = sms.getMensaje();
        String[] cuerpo = contenido.split("#");
//        System.out.println("El dispatcher recive "+contenido);
        boolean pasaHash = false;
        try {
            pasaHash = ValidatingTools.checkHash(cuerpo);
        } catch (Exception e) {
            System.out.println("El modem dispatcher no pudo chequear el hash del mensaje " + contenido.toString());
        }
        if (pasaHash) {
            if (ValidatingTools.validar(contenido, DataSource.terminal_remota) 
                    && cuerpo[0].equalsIgnoreCase("L") || cuerpo[0].equalsIgnoreCase("M")) {
                
                if (cuerpo[2].split("\\|")[0].equalsIgnoreCase("Raise")) {
//                        System.out.println("El dispatcher manda "+contenido);
                    startupSalida.add(sms);
                } else {
//                        System.out.println("El dispatcher manda a datos "+contenido);
                    dataSalida.add(sms);
                }
            } else {
                System.out.println("El dispatcher rebota: " + contenido);
            }
        } else {
            System.out.println("El dispatcher rebota por hash: " + contenido);
        }
    }
    
}
