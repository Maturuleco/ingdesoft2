package sensorandadaptercomunication;

import java.io.File;
import java.io.FileOutputStream;
import model.FactorClimatico;

public class Sensor implements Runnable {

    private static final int maxSize = 999999999;
    
    private String name;
    private FactorClimatico factor;
    private long frequency;
    private String directory;
    private Thread thread = null;   
    
    public Sensor(String directory, String nombre, FactorClimatico factor, long frequency) {
        setName(nombre);
        setFactor(factor);
        setDirectory(directory);
        setFrequency(frequency);
    }

    public void run() {
        
        int numSMS = 0;
        int numFreq = 1;
        String path = directory;
        File folder = new File (directory);
        
        while(true){
            path = directory;
            try {
                if (numFreq == frequency){
                    numFreq=0;
                    File[] files = folder.listFiles();
                    
                    // Solo se puede crear un mensaje nuevo
                    // si la carpeta no esta "llena"
                    if(files.length < 10){
                        path +=  "sms"+ numSMS +".txt";
                        
                        File file = new File(path);
                        FileOutputStream fos = new FileOutputStream(file);
                        SensorSMS sms = new SensorSMS(this);
                        for (char ch : sms.getInfo().toCharArray()) {
                            fos.write(ch);
                        }
                        fos.close();
                    }
                    numSMS++;
                    if( numSMS > maxSize )
                        numSMS = 0;
                }
            } catch (Exception e) {
              e.printStackTrace(System.err);
            }
            numFreq++;
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

    public FactorClimatico getFactor() {
        return factor;
    }
    
    public void setFactor(FactorClimatico factor) {
        this.factor = factor;
    }
    
}
