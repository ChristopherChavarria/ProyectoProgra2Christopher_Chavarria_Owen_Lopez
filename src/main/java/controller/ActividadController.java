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
import modelo.Actividad;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ActividadController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private DatePicker dpFecha;
    @FXML private TextField txtHoraInicio;
    @FXML private TextField txtHoraFin;
    @FXML private TextField txtUbicacion;
    @FXML private Spinner<Integer> spnCapacidadMax;

    @FXML private Button btnGuardar;
    @FXML private Button btnVolver;

    @FXML private TableView<Actividad> tableActividades;
    @FXML private TableColumn<Actividad, Integer> colId;
    @FXML private TableColumn<Actividad, String> colNombre;
    @FXML private TableColumn<Actividad, String> colDescripcion;
    @FXML private TableColumn<Actividad, Date> colFecha;
    @FXML private TableColumn<Actividad, Time> colHoraInicio;
    @FXML private TableColumn<Actividad, Time> colHoraFin;
    @FXML private TableColumn<Actividad, String> colUbicacion;
    @FXML private TableColumn<Actividad, Integer> colCapacidad;

    private ObservableList<Actividad> listaActividades = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 30);
        spnCapacidadMax.setValueFactory(valueFactory);

        txtNombre.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches(".*\\d.*") ? null : change));
        txtDescripcion.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches(".*\\d.*") ? null : change));
        txtUbicacion.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches(".*\\d.*") ? null : change));

        txtHoraInicio.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("[0-9:]*") ? change : null));
        txtHoraFin.setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("[0-9:]*") ? change : null));
        spnCapacidadMax.getEditor().setTextFormatter(new TextFormatter<>(change -> change.getControlNewText().matches("\\d*") ? change : null));

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
        colHoraFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadMax"));
        cargarDatosTabla();
    }

    public void cargarDatosTabla() {
        listaActividades.clear();
        String sql = "SELECT id, nombre, descripcion, fecha, hora_inicio, hora_finalizacion, ubicacion, capacidad_max, estado FROM actividad";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDate("fecha"),
                    rs.getTime("hora_inicio"),
                    rs.getTime("hora_finalizacion"),
                    rs.getString("ubicacion"),
                    rs.getInt("capacidad_max"),
                    rs.getString("estado")
                );
                listaActividades.add(actividad);
            }
            tableActividades.setItems(listaActividades);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private LocalTime parsearHoraInteligente(String textoHora) {
        textoHora = textoHora.trim();
        if (textoHora.matches("^\\d{1,2}$")) {
            textoHora = String.format("%02d:00:00", Integer.parseInt(textoHora));
        } else if (textoHora.matches("^\\d{1,2}:\\d{2}$")) {
            textoHora = textoHora + ":00";
        }
        return LocalTime.parse(textoHora, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    @FXML
    void guardarActividad(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        LocalDate fecha = dpFecha.getValue();
        String horaInicioStr = txtHoraInicio.getText().trim();
        String horaFinStr = txtHoraFin.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();
        int capacidadMax = spnCapacidadMax.getValue();

        if (nombre.isEmpty() || descripcion.isEmpty() || fecha == null || horaInicioStr.isEmpty() || horaFinStr.isEmpty() || ubicacion.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Campos Incompletos");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos de la actividad.");
            alert.showAndWait();
            return;
        }

        LocalTime horaInicio;
        LocalTime horaFin;
        try {
            horaInicio = parsearHoraInteligente(horaInicioStr);
            horaFin = parsearHoraInteligente(horaFinStr);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Formato de Hora Inválido");
            alert.setHeaderText(null);
            alert.setContentText("Formato de hora no reconocido. Usa formatos como: 4, 14:30 o 14:30:00");
            alert.showAndWait();
            return;
        }

        // Si tu base de datos requiere IDs válidos para evento_id, expositor_id o tipo_actividad_id,
        // asegúrate de que existan registros con ID 1 en esas tablas, o usa NULL si las columnas lo permiten.
        String sql = "INSERT INTO actividad (nombre, descripcion, fecha, hora_inicio, hora_finalizacion, ubicacion, capacidad_max, estado, evento_id, expositor_id, tipo_actividad_id) VALUES (?, ?, ?, ?, ?, ?, ?, 'abierta', 1, 1, 1)";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.setDate(3, Date.valueOf(fecha));
            pstmt.setTime(4, Time.valueOf(horaInicio));
            pstmt.setTime(5, Time.valueOf(horaFin));
            pstmt.setString(6, ubicacion);
            pstmt.setInt(7, capacidadMax);
            pstmt.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("¡Actividad guardada correctamente!");
            alert.showAndWait();

            txtNombre.clear();
            txtDescripcion.clear();
            dpFecha.setValue(null);
            txtHoraInicio.clear();
            txtHoraFin.clear();
            txtUbicacion.clear();
            spnCapacidadMax.getValueFactory().setValue(30);
            
            cargarDatosTabla();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error de Base de Datos");
            alert.setHeaderText("No se pudo registrar la actividad");
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