package excepciones;

/**
 * La actividad o su evento no aceptan inscripciones en su estado actual.
 */
public class ActividadNoDisponibleException extends NegocioException {

    public ActividadNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
