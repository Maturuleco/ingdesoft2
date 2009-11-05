package networkController;

public class TimerTR implements Runnable {
    private String trName;
    private int intervalo = 16000;
    private boolean fall = false;
    private boolean repeticion = true;
    private boolean enEjecucion = false;
    private Thread thread = null;
    private NetworkController handler = null;

    // Contructor basico, usado por defecto
    TimerTR() { }

    TimerTR(String name, int tiempo,NetworkController handler ) {
        setTrName(name);
        setIntervalo(tiempo);
        setHandler( handler );
    }

    // Fija el objeto que ha de ser notificado de los eventos que sucedan al timer
    public void setHandler( NetworkController handler ) {
        this.handler = handler;
    }

    // Fija el numero de milisegundos entre pulsos del timer
    public void setIntervalo( int intervalo ) {
        this.intervalo = intervalo;
    }

    /*
    * Aqui creamos un nuevo thread para correr el Timer. Lo incializamos
    * con "this" de forma que el metodo run() se llame inmediatamente
    * como comience la ejecucion del thread
    */
     public void start() {
        thread = new Thread( this );
        enEjecucion = true;
        thread.start();
    }

    public void stop() {
        enEjecucion = false;
        thread.stop();
    }

    public void run() {
        repeticion = true;

        init();
        
        while( repeticion ){
            // Esperamos el tiempo que nos hayan dicho en la configuracion
            // del intervalo
            try {
                esperar( intervalo );
            } catch( InterruptedException e ) {
                return;
            }
            // Cuando se cumple el intervalo, avisamos a las clases que
            // esten pendientes.
            handler.timeout( this );
            
            System.out.println("\n*********NC TR: "+getTrName()+" caida*********\n");
            setTimerTRFall(true);
            stop();

            repeticion = false;
            enEjecucion = false;
        }
    }

    public boolean estaCorriendo() {
        return enEjecucion ;
    }

    private synchronized void esperar( int lapso ) throws InterruptedException {
        thread.sleep(lapso);
    }

    public String getTrName() {
        return trName;
    }

    public void setTrName(String trName) {
        this.trName = trName;
    }

    public void setTimerTRFall(boolean value){
        fall = value;
    }
    public boolean getTimerTRFall(){
        return fall;
    }
    
    @Override
    public String toString(){
 	return  "TR NAME: " +getTrName() + "/n";
    }

    private void init() {
        if(thread == null)
            start();
    }
}