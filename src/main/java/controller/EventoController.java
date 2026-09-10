package controller;

import conexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.Evento;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventoController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private DatePicker dpFecha;
    @FXML private TextField txtLugar;
    @FXML private Button btnGuardar;
    @FXML private Button btnVolver;

    @FXML private TableView<Evento> tableEventos;
    @FXML private TableColumn<Evento, Integer> colId;
    @FXML private TableColumn<Evento, String> colNombre;
    @FXML private TableColumn<Evento, String> colDescripcion;
    @FXML private TableColumn<Evento, Date> colFechaInicio;
    @FXML private TableColumn<Evento, String> colLugar;
    @FXML private TableColumn<Evento, String> colEstado;

    private ObservableList<Evento> listaEventos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bloquear números en campos de texto de letras
        txtNombre.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches(".*\\d.*") ? null : change));
        txtDescripcion.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches(".*\\d.*") ? null : change));
        txtLugar.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches(".*\\d.*") ? null : change));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        cargarDatosTabla();
    }

    public void cargarDatosTabla() {
        listaEventos.clear();
        String sql = "SELECT id, nombre, descripcion, fecha_inicio, fecha_fin, lugar, estado FROM evento";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Evento evento = new Evento(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDate("fecha_inicio"),
                    rs.getDate("fecha_fin"),
                    rs.getString("lugar"),
                    rs.getString("estado")
                );
                listaEventos.add(evento);
            }
            tableEventos.setItems(listaEventos);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void guardarEvento(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        LocalDate fecha = dpFecha.getValue();
        String lugar = txtLugar != null ? txtLugar.getText().trim() : "Auditorio Principal";

        if (nombre.isEmpty() || descripcion.isEmpty() || fecha == null || lugar.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campos Incompletos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos del evento.");
            alert.showAndWait();
            return;
        }

        String sql = "INSERT INTO evento (nombre, descripcion, fecha_inicio, fecha_fin, lugar, estado) VALUES (?, ?, ?, ?, ?, 'planificacion')";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setDate(3, Date.valueOf(fecha));
            pstmt.setDate(4, Date.valueOf(fecha)); // Usamos la misma fecha como fin por defecto
            pstmt.setString(5, lugar);
            pstmt.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("¡Evento guardado correctamente en la base de datos!");
            alert.showAndWait();

            txtNombre.clear();
            txtDescripcion.clear();
            dpFecha.setValue(null);
            if (txtLugar != null) txtLugar.clear();
            
            cargarDatosTabla();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de Base de Datos");
            alert.setHeaderText("No se pudo registrar el evento");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void regresarPrincipal(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/app/principal.xml"));
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestor de Eventos - Primer Avance");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}