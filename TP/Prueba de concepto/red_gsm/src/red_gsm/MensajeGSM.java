package red_gsm;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;

public class MensajeGSM {
    private Integer destino;
    private Integer origen;
    private Integer priority;
    private String mensaje;
    private Date fecha;


    public Integer getPriority() {
        return priority;
    }

    public MensajeGSM(int orig, int dest, String msj, int priority)
    {
            this.priority = priority;
            destino = dest;
            origen = orig;
            mensaje = new String(msj);
            fecha = new Date();
            /*
            // Lo que sobra lo tira...
            mensaje = new char[160];
            int lon = msj.length();
            for (int i=0; i < mensaje.length(); i++) {
                    if (lon >= i)
                        mensaje[i] = msj[i] ;
                    else
                            mensaje[i] = ' ';
            }
            */
    }

    public Integer getDestino()
    {
            return destino;
    }

    public Integer getOrigen()
    {
            return origen;
    }

    public String getMensaje()
    {
            return mensaje;
    }

    public Date getFecha()
    {
            return fecha;
    }

    private void setFecha(Date fecha) {
        this.fecha = fecha;
    }



    @Override
    public String toString(){
        String orig = origen.toString();
        String dest = destino.toString();
        String fech = String.valueOf( fecha.getTime() );
        String prioridad = priority.toString();
        return (orig+";"+dest+";"+fech+";"+prioridad+";"+mensaje);
    }

    public static MensajeGSM parse(String msj) throws ParseException {
        String[] partes = msj.split(";");

        if (partes.length < 5)
            throw new ParseException(msj, 0);

        Integer origen = Integer.valueOf(partes[0]);
        Integer destino = Integer.valueOf(partes[1]);
        Date fecha = new Date(Long.valueOf(partes[2]));
        Integer prioridad = Integer.valueOf(partes[3]);
        String cuerpo = partes[4];

        MensajeGSM mensaje = new MensajeGSM(origen, destino, cuerpo,prioridad);
        mensaje.setFecha(fecha);
        
        return mensaje;
    }
}
