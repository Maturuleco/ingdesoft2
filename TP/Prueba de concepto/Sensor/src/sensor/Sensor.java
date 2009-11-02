package sensor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FactorClimatico;

public class Sensor implements Runnable {

    private static final int maxSize = 999999999;
    
    private Integer name;
    private FactorClimatico factor;
    private long frequency;
    private String directory;
    private Thread thread = null;   
    
    public Sensor(String directory, Integer nombre, FactorClimatico factor, long frequency) {
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

                if (numFreq == frequency){
                    numFreq=0;
                    File[] files = folder.listFiles();
                    
                    // Solo se puede crear un mensaje nuevo
                    // si la carpeta no esta "llena"
                    if(files.length < 10){
                    FileOutputStream fos = null;
                    try {
                        path += "dato" + numSMS + ".txt";
                        File file = new File(path);
                        fos = new FileOutputStream(file);
                        SensorSMS sms = new SensorSMS(this);
                        for (char ch : sms.getInfo().toCharArray()) {
                            fos.write(ch);
                        }
                        System.out.println("\nEl Sensor ha escrito: " + sms.getInfo());
                        fos.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    }
                    numSMS++;
                    if( numSMS > maxSize )
                        numSMS = 0;
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

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public FactorClimatico getFactor() {
        return factor;
    }
    
    public void setFactor(FactorClimatico factor) {
        this.factor = factor;
    }
    
}
