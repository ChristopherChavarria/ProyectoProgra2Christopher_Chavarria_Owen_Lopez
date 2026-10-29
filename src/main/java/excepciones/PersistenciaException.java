package excepciones;

/**
 * Envuelve los errores de JDBC (conexión, SQL, etc.) para que las capas
 * superiores no dependan de SQLException.
 */
public class PersistenciaException extends RuntimeException {

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
