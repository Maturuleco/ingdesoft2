package sensorandadaptercomunication;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class MensajeSMSInterno {
    
    private String sensorName;
    private String sensorType;
    private String sensorValue;
    private Date sensorTimestamp;

    MensajeSMSInterno(String texto) {
        try {
            String[] partes = texto.split("\\|");

            if (partes.length < 4) {
                throw new ParseException(texto, 0);
            }
            setSensorName(partes[0]);
            setSensorType(partes[1]);
            setSensorValue(partes[2]);
            setSensorTimestamp(new Date(Long.valueOf(partes[3])));
            
        } catch (ParseException ex) {
            Logger.getLogger(MensajeSMSInterno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Date getSensorTimestamp() {
        return sensorTimestamp;
    }

    public void setSensorTimestamp(Date sensorTimestamp) {
        this.sensorTimestamp = sensorTimestamp;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
    }
    
}
