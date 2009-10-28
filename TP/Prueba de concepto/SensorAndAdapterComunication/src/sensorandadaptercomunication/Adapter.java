package sensorandadaptercomunication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;

public class Adapter implements Runnable {
    
    private String name;
    private long frequency;
    private String directory;
    private Thread thread = null;   
    private BlockingQueue<MensajeSMSInterno> salida;
    
    public Adapter(String directory, String nombre, long frequency) {
        setName(nombre);
        setDirectory(directory);
        setFrequency(frequency);
    }

    public void run() {
        int i = 1;
        File folder = new File (directory);
        
        while(true){
            try {
                if (i == frequency){
                    i=0;
                    File[] files = folder.listFiles();
                    for (int j = 0; j < files.length; j++) {
                            File file = files[j];
                            if(file.canRead()){
                                FileReader fr = new FileReader(file);
                                BufferedReader br = new BufferedReader(fr);
                                String texto = br.readLine();
                                file.delete();
                                
                                // Se envia el mensaje a quien corresponda
                                MensajeSMSInterno mensaje = new MensajeSMSInterno(texto);
                                salida.put(mensaje);
                            }
                        }
                    }
            } catch (Exception e) {
              e.printStackTrace(System.err);
            }
            i++;
        }
    }

    public void setSalida(BlockingQueue<MensajeSMSInterno> salida) {
        this.salida = salida;
    }
    
    public void start() {
        thread = new Thread( this );
        thread.start();
    }
    
    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
