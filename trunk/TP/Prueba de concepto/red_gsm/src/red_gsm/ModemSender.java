/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package red_gsm;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author tas
 */
public class ModemSender extends Thread {

    private static final long timeToWait = 100;
    private static final int maxSize = 999999999;
    private int numero;
    private int numSms = 1;
    private String redDirectory = Red.path;
    private BlockingQueue<MensajeGSM> entradaDatos;

    public ModemSender(int numero) {
        this.numero = numero;
 //       this.redDirectory = redDirectory;
    }

    public void setEntradaDatos(BlockingQueue<MensajeGSM> entradaDatos) {
        this.entradaDatos = entradaDatos;
    }


    private void send(MensajeGSM mensaje)
    {
        MensajeGSM msj = new MensajeGSM(numero, mensaje.getDestino(), mensaje.getMensaje());
        String path = redDirectory;
        
        String phrase = msj.toString();
        
        try {
            path += "/" + numero + "-" + numSms +".txt";
            //System.out.println(getName() + " C: " +"sms"+ j +".txt");

            File file = new File(path);
            FileOutputStream fos = new FileOutputStream(file);

            for (char ch : phrase.toCharArray()) {
                fos.write(ch);
            }
            fos.close();
            numSms++;
            if( numSms > maxSize )
                numSms = 0;

        } catch (Exception e) {
                  e.printStackTrace(System.err);
        }
    }
    
    private boolean sensarEntradaSistema() {
        MensajeGSM cabeza = entradaDatos.poll();
        if (cabeza != null) {
            send(cabeza);
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        
        while (true) {
            if (! sensarEntradaSistema() ) {
                try {
                    // Duermo un segundo
                    sleep(timeToWait);
                } catch (InterruptedException ex) {}
            }
        }
        
    }
    
    
    
}
