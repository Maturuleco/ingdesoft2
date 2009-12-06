package EstadoDeRed;

import java.util.Date;

public class HeartbeatMessege extends MensajeNetworkController {

    private String sensorName;
    private Date sensorTimestamp;

    public HeartbeatMessege(Integer idTR, Date trTimestamp, String sensorName, Date sensorTimestamp){
        
        setIdTR(idTR);
        setFecha(trTimestamp);
        this.sensorName = sensorName;
        this.sensorTimestamp = sensorTimestamp;       
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

   
    @Override
     public String toString(){
        return " ID TR: " + this.getIdTR();
 //              " SENSOR NAME: " + this.sensorName +
 //              " TR TIME: " + this.trTimestamp +
 //              " SENSOR TIME: " + this.sensorTimestamp;
    }
}
