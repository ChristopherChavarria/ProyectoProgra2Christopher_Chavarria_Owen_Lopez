package excepciones;

/**
 * El participante ya tiene una inscripción activa en la actividad.
 */
public class InscripcionDuplicadaException extends NegocioException {

    public InscripcionDuplicadaException(String mensaje) {
        super(mensaje);
    }
}
