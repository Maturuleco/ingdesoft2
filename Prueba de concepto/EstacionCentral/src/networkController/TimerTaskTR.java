package networkController;

import java.util.TimerTask;

public class TimerTaskTR extends TimerTask {

    private Integer trName;
    private NetworkController handler = null;

    TimerTaskTR(NetworkController handler, Integer name ) {
        setTrName(name);
        setHandler( handler );
    }

    public void setHandler( NetworkController handler ) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.timeout(trName);
        // TODO: revisar este cancel!!!
        this.cancel();
    }

    public Integer getTrName() {
        return trName;
    }

    public void setTrName(Integer trName) {
        this.trName = trName;
    }
    
    @Override
    public String toString(){
 	return  "TR NAME: " +getTrName() + "/n";
    }

}