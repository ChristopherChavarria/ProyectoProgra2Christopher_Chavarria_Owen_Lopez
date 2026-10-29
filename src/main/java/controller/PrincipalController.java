
package controller;

import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PrincipalController {

    @FXML
    private Button btnGestionEventos;

    @FXML
    private Button btnGestionActividades;

    @FXML
    private void abrirGestionEventos(ActionEvent event) {
        abrirModulo("/app/evento.xml", "Módulo de Gestión de Eventos");
    }

    @FXML
    private void abrirGestionActividades(ActionEvent event) {
        abrirModulo("/app/actividad.xml", "Módulo de Gestión de Actividades");
    }

    private void abrirModulo(String ruta, String titulo) {
        try {
            URL recurso = getClass().getResource(ruta);

            if (recurso == null) {
                throw new IOException("No se encontró el archivo: " + ruta);
            }

            FXMLLoader loader = new FXMLLoader(recurso);
            Parent root = loader.load();

            Stage stage = (Stage) btnGestionEventos.getScene().getWindow();

            boolean maximizada = stage.isMaximized();

            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.setMaximized(maximizada);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();

            String detalle = e.getMessage();

            if (detalle == null) {
                detalle = e.toString();
            }

            Throwable causa = e.getCause();

            while (causa != null) {
                detalle += "\nCausa: " + causa.toString();
                causa = causa.getCause();
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Navegación");
            alert.setHeaderText("No se pudo abrir el módulo");
            alert.setContentText(detalle);
            alert.getDialogPane().setPrefWidth(650);
            alert.showAndWait();
        }
    }
}
