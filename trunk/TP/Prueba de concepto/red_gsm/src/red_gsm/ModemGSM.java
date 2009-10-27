package red_gsm;

import java.io.BufferedReader;
import java.io.File;
import java.util.concurrent.BlockingQueue;


public class ModemGSM extends Thread {
    private static final int maxSize = 999999999;
    private int numero;
    private int numSms = 1;
    private File folderPropia;
            
    private String redDirectory;
    private BlockingQueue<MensajeGSM> salida;
    private BlockingQueue<MensajeToModemGSM> entrada;
    private ModemSender modemSender;
    private ModemReciver modemReciver;
    
    public ModemGSM(int num, String dirRed)
    {
        numero = num;
        redDirectory = dirRed;
        folderPropia = new File ("GSM/"+numero);
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
        modemSender = new ModemSender(numero, redDirectory);
        modemSender.setEntradaDatos(entrada);
        modemReciver = new ModemReciver(folderPropia);
        modemReciver.setSalida(salida);
        
        modemSender.start();
        modemReciver.start();
    }

}
