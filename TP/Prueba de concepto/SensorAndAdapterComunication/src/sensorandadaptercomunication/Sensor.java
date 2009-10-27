package sensorandadaptercomunication;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class Sensor implements Runnable {

    private long frequency;
    private String name;
    private String directory;
    private Thread thread = null;   
    
    public Sensor(String directory, String nombre, long frequency) {
        setName(nombre);
        setDirectory(directory);
        setFrequency(frequency);
    }

    public void run() {
        String phrase = getInfo();
        int i = 1;
        File folder = new File (directory);
        String path = directory;
        int j = 0;
        while(true){
            path = directory;
            try {
                if (i == frequency){
                    i=0;
                    File[] files = folder.listFiles();
                    // Solo se puede crear un mensaje nuevo si la carpeta no esta "llena"
                    if(files.length < 10){
                        path +=  "sms"+ j +".txt";
                        System.out.println(getName() + " C: " +"sms"+ j +".txt");
                        
                        File file = new File(path);
                        FileOutputStream fos = new FileOutputStream(file);
                        
                        for (char ch : phrase.toCharArray()) {
                            fos.write(ch);
                        }
                        fos.close();
                    }else{
                        System.out.println(getName() + ": no lugar ");
                    }
                    j++;
                    if( j > 999999999 )
                        j = 0;
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

    private String getInfo() {
       Date d = new Date();
        String info = getName() + "," +
                      getType() + "," +
                      System.currentTimeMillis() + "," +
                      d.toString();
                     
        return info;
    }

    private String getType() {
        if(frequency%30==0){
            return FactorConstants.HUM;
        } else if(frequency%20==0){
            return FactorConstants.VIENTO;
        } else{
            return FactorConstants.TEMP;
        }
    }
    
    
}
