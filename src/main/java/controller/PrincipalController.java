package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

public class PrincipalController {

    @FXML
    private Button btnGestionEventos;

    @FXML
    private Button btnGestionActividades;

    @FXML
    void abrirGestionEventos(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/evento.xml"));
            Stage stage = (Stage) btnGestionEventos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Módulo de Gestión de Eventos");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de Navegación");
            alert.setHeaderText("No se pudo abrir el módulo de eventos");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // ¡Aquí está el cambio del nombre! (abrirGestionActividades)
    @FXML
    void abrirGestionActividades(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/actividad.xml"));
            Stage stage = (Stage) btnGestionActividades.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestión de Actividades");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de Navegación");
            alert.setHeaderText("No se pudo abrir el módulo de actividades");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
