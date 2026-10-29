package conexion;

import excepciones.PersistenciaException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Fábrica de conexiones JDBC.
 * Los datos de acceso se leen de db.properties (en la carpeta del proyecto),
 * que NO se sube a GitHub para no publicar la contraseña.
 */
public class Conexion {

    private static final String ARCHIVO = "db.properties";
    private static Properties config;

    private Conexion() {
    }

    public static Connection obtenerConexion() {
        Properties p = configuracion();
        try {
            return DriverManager.getConnection(p.getProperty("db.url"),
                    p.getProperty("db.usuario"), p.getProperty("db.clave", ""));
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo conectar con la base de datos. "
                    + "Revise su conexión a internet y los datos de db.properties.", e);
        }
    }

    private static synchronized Properties configuracion() {
        if (config == null) {
            Properties p = new Properties();
            try (InputStream in = new FileInputStream(ARCHIVO)) {
                p.load(in);
            } catch (IOException e) {
                throw new PersistenciaException("No se encontró el archivo " + ARCHIVO
                        + ". Copie db.properties.ejemplo como db.properties y complete sus datos.", e);
            }
            config = p;
        }
        return config;
    }
}
