package excepciones;

/**
 * Operación que viola una regla de negocio (p. ej. borrar un evento con actividades).
 */
public class OperacionNoPermitidaException extends NegocioException {

    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}
