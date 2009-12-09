package RequerimientosModelos;

/**
 *
 * @author Ce y Mat
 */
public class RequerimientoResultado extends Requerimiento {
    private Integer modelo;
    private Integer trID;

    public RequerimientoResultado(Integer EcProveedora, Integer modelo, Integer trID) {
        super(EcProveedora);
        this.modelo = modelo;
        this.trID = trID;
    }

    public Integer getModelo() {
        return modelo;
    }

    public Integer getTrID() {
        return trID;
    }
    
}
