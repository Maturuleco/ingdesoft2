package Adapters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import model.DatoSensado;
import model.FactorClimatico;

public class Adapter implements Runnable {
    
    private Integer name;
    private long frequency;
    private String directory;
    private Thread thread = null;   
    private BlockingQueue<DatoSensado> salida;
    
    public Adapter(String directory, Integer nombre, long frequency) {
        setName(nombre);
        setDirectory(directory);
        setFrequency(frequency);
    }

    public void run() {
        int i = 1;
        File folder = new File (directory);
        
        while(true){
            try {
                File[] files = folder.listFiles( );
                if (files != null) {
                    for (int j = 0; j < files.length; j++) {
                        File file = files[j];
                        if(file.canRead()){
                            FileReader fr = new FileReader(file);
                            BufferedReader br = new BufferedReader(fr);
                            String texto = br.readLine();
                            file.delete();

                            // Se envia el mensaje a quien corresponda
                            DatoSensado mensaje = parsear(texto);
                            salida.put(mensaje);
                        }
                    }
                }
            } catch (Exception e) {
              e.printStackTrace(System.err);
            }
        }
    }

    public static Adapter parse(String texto) throws ParseException {
        String[] partes = texto.split("\\|");

        if (partes.length < 3) {
            throw new ParseException(texto, 0);
        }
        String path = partes[0];
        Integer id = Integer.valueOf(partes[1]);
        long frecuencia = Long.valueOf(partes[2]);

        return new Adapter(path, id, frecuencia);
    }

    private DatoSensado parsear(String texto) throws ParseException {
        String[] partes = texto.split("\\|");

        if (partes.length < 4) {
            throw new ParseException(texto, 0);
        }
        Integer id = Integer.valueOf(partes[0]);
        FactorClimatico fc = FactorClimatico.parse(partes[1]);
        Float valor = Float.valueOf(partes[2]);
        Date date = new Date(Long.valueOf(partes[3]));

        return new DatoSensado(id, date, fc, valor);
    }

    public void setSalida(BlockingQueue<DatoSensado> salida) {
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

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }
}
