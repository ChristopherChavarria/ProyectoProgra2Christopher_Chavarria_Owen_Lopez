package excepciones;

/**
 * Se buscó un registro (evento, actividad, persona...) que no existe.
 */
public class RegistroNoEncontradoException extends NegocioException {

    public RegistroNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
