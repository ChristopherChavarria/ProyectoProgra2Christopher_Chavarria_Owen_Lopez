package excepciones;

/**
 * Datos ingresados no válidos (campo vacío, formato, fechas, etc.).
 */
public class DatosInvalidosException extends NegocioException {

    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
