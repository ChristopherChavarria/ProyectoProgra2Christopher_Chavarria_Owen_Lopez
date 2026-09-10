package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
   private static final String URL = "jdbc:mysql://mysql.us.cloudlogin.co:3306/gamabasis_piig8?useSSL=false&serverTimezone=UTC";
    private static final String USER = "gamabasis_piig8";
    
   private static final String PASSWORD = "plMH9Tu55#";

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a la base de datos!");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }
}