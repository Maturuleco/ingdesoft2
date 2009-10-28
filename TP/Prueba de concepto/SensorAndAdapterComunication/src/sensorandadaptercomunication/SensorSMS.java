package sensorandadaptercomunication;

import java.util.Date;

public class SensorSMS {
    
    private Sensor sensor;

    SensorSMS(Sensor sensor) {
        setSensor(sensor);
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
    
    /*
     * Se genera un mensaje con la info necesaria.
     */
    public String getInfo() {
       Date d = new Date();
       String info = sensor.getName().toString() + "|" +
                     sensor.getFactor().toString() + "|" +
                     System.currentTimeMillis() + "|" +
                     d.getTime();
                     
        return info;
    }

}
