package red_gsm;

import java.io.File;
import java.util.concurrent.BlockingQueue;


public class ModemGSM extends Thread {
    private static final int maxSize = 999999999;
    private int numero;
    private int numSms = 1;
    private File folderPropia;
            
    private String redDirectory = Red.path;
    private BlockingQueue<MensajeGSM> salida;
    private BlockingQueue<MensajeToModemGSM> entrada;
    private ModemSender modemSender;
    private ModemReciver modemReciver;
    
    public ModemGSM(int num)
    {
        numero = num;
   //     redDirectory = dirRed;
        folderPropia = new File (redDirectory+numero);
        folderPropia.mkdir();
    }

    public void setEntrada(BlockingQueue<MensajeToModemGSM> entrada) {
        this.entrada = entrada;
    }

    public void setSalida(BlockingQueue<MensajeGSM> salida) {
        this.salida = salida;
    }

    @Override
    public void run() {
        MensajeToModemGSM mensajeInicio = new MensajeToModemGSM(0, "Mensaje_de_Inicio_de_Sesion");
        entrada.offer(mensajeInicio);
        modemSender = new ModemSender(numero);
        modemSender.setEntradaDatos(entrada);
        modemReciver = new ModemReciver(folderPropia);
        modemReciver.setSalida(salida);
        
        modemSender.start();
        modemReciver.start();
    }

}
