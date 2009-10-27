package sensorandadaptercomunication;

public class Main {

    public static void main(String[] args) {
         for (int i = 1; i <= 3; ++i){
            String path = "C:/Documents and Settings/Lionel Raymundi/Escritorio/sensor"+i+"/";
            
            Sensor s = new Sensor(path, "sensor"+i, 10*i);
            s.start();
            Adapter a = new Adapter(path, "sensor"+i, 20*i);
            a.start();
            
         }
            
    }

}
