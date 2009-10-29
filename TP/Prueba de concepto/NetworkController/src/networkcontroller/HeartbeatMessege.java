package networkcontroller;

import java.util.Date;

public class HeartbeatMessege {

    private Integer idTR;
    private Date trTimestamp;

    private String sensorName;
    private Date sensorTimestamp;

    public HeartbeatMessege(Integer idTR, Date trTimestamp, String sensorName, Date sensorTimestamp){
        this.idTR = idTR;
        this.sensorName = sensorName;
        this.trTimestamp = trTimestamp;
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

    public Integer getTrName() {
        return idTR;
    }

    public void setTrName(Integer idTR) {
        this.idTR = idTR;
    }

    public Date getTrTimestamp() {
        return trTimestamp;
    }

    public void setTrTimestamp(Date trTimestamp) {
        this.trTimestamp = trTimestamp;
    }
    
}
