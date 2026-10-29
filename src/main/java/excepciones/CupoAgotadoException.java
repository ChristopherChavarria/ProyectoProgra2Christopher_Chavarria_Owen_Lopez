package excepciones;

/**
 * La actividad ya alcanzó su capacidad máxima.
 */
public class CupoAgotadoException extends NegocioException {

    public CupoAgotadoException(String mensaje) {
        super(mensaje);
    }
}
