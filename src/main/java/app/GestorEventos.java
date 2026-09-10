package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestorEventos extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/principal.xml"));
            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Gestor de Eventos - Primer Avance");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar la interfaz gráfica: " + e.getMessage());
        }
    }
}