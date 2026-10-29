package excepciones;

/**
 * Excepción base para cualquier regla de negocio que no se cumple.
 * Los mensajes están pensados para mostrarse directamente al usuario.
 */
public class NegocioException extends Exception {

    public NegocioException(String mensaje) {
        super(mensaje);
    }

    public NegocioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
