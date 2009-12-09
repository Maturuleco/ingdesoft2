
package RequerimientosModelos;

import SubscripcionesEc.CreadorPedidosSubscripciones;
import SubscripcionesEc.SubscriberMessage;

/**
 *
 * @author Ce y Mat
 */
public abstract class Requerimiento {
    private Integer EcProveedora;

    public Requerimiento(Integer EcProveedora) {
        this.EcProveedora = EcProveedora;
    }
    
    public Integer getProveedor() {
        return EcProveedora;
    }
    
    public abstract SubscriberMessage crearSubscripcion(Integer idSubscriptor, CreadorPedidosSubscripciones creador);

}
