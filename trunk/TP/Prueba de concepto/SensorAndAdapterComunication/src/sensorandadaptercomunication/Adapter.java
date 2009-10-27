package sensorandadaptercomunication;

import java.io.File;
import java.io.FileInputStream;

public class Adapter implements Runnable {
    
    private long frequency;
    private String name;
    private String directory;
    private Thread thread = null;   
    
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
                            System.out.println( getName()+ "    D:" + file.getName());
                            if(file.canRead()){
                                // LEER INFO  Y BORRAR
                                file.delete();
                            }
                            
                        }
                    }
            } catch (Exception e) {
              e.printStackTrace(System.err);
            }
            i++;
        }
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
