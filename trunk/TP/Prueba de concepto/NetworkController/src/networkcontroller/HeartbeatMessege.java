package networkcontroller;

import java.util.Date;

public class HeartbeatMessege {

    private String trName;
    private Date trTimestamp;

    private String sensorName;
    private Date sensorTimestamp;

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

    public String getTrName() {
        return trName;
    }

    public void setTrName(String trName) {
        this.trName = trName;
    }

    public Date getTrTimestamp() {
        return trTimestamp;
    }

    public void setTrTimestamp(Date trTimestamp) {
        this.trTimestamp = trTimestamp;
    }
    
}
