
package controller;

import conexion.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import modelo.Actividad;

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

    private final ObservableList<Actividad> listaActividades =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1, 500, 30
                );

        factory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer valor) {
                return valor == null ? "" : valor.toString();
            }

            @Override
            public Integer fromString(String texto) {
                return Integer.valueOf(texto.trim());
            }
        });

        spnCapacidadMax.setValueFactory(factory);
        spnCapacidadMax.setEditable(true);

        txtNombre.setTextFormatter(new TextFormatter<>(
                cambio -> cambio.getControlNewText().matches(".*\\d.*")
                        ? null : cambio
        ));

        txtDescripcion.setTextFormatter(new TextFormatter<>(
                cambio -> cambio.getControlNewText().matches(".*\\d.*")
                        ? null : cambio
        ));

        txtUbicacion.setTextFormatter(new TextFormatter<>(
                cambio -> cambio.getControlNewText().matches(".*\\d.*")
                        ? null : cambio
        ));

        txtHoraInicio.setTextFormatter(new TextFormatter<>(
                cambio -> cambio.getControlNewText().matches("[0-9:]*")
                        ? cambio : null
        ));

        txtHoraFin.setTextFormatter(new TextFormatter<>(
                cambio -> cambio.getControlNewText().matches("[0-9:]*")
                        ? cambio : null
        ));

        spnCapacidadMax.getEditor().setTextFormatter(
                new TextFormatter<>(
                        cambio -> cambio.getControlNewText().matches("\\d*")
                                ? cambio : null
                )
        );

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        colDescripcion.setCellValueFactory(
                new PropertyValueFactory<>("descripcion")
        );

        colFecha.setCellValueFactory(
                new PropertyValueFactory<>("fecha")
        );

        colHoraInicio.setCellValueFactory(
                new PropertyValueFactory<>("horaInicio")
        );

        colHoraFin.setCellValueFactory(
                new PropertyValueFactory<>("horaFin")
        );

        colUbicacion.setCellValueFactory(
                new PropertyValueFactory<>("ubicacion")
        );

        colCapacidad.setCellValueFactory(
                new PropertyValueFactory<>("capacidadMax")
        );

        tableActividades.setItems(listaActividades);

        tableActividades.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        cargarDatosTabla();
    }

    public void cargarDatosTabla() {

        String sql = "SELECT id, nombre, descripcion, fecha, "
                + "hora_inicio, hora_finalizacion, ubicacion, "
                + "capacidad_max, estado FROM actividad";

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            ObservableList<Actividad> resultados =
                    FXCollections.observableArrayList();

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

                resultados.add(actividad);
            }

            listaActividades.setAll(resultados);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error de base de datos",
                    "No se pudieron cargar las actividades: "
                    + e.getMessage()
            );
        }
    }

    private LocalTime parsearHoraInteligente(String textoHora) {

        textoHora = textoHora.trim();

        if (textoHora.matches("\\d{1,2}")) {
            textoHora = String.format(
                    "%02d:00:00",
                    Integer.parseInt(textoHora)
            );
        } else if (textoHora.matches("\\d{1,2}:\\d{2}")) {
            textoHora = textoHora + ":00";
        }

        return LocalTime.parse(
                textoHora,
                DateTimeFormatter.ofPattern("H:mm:ss")
        );
    }

    @FXML
    private void guardarActividad(ActionEvent event) {

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        LocalDate fecha = dpFecha.getValue();
        String horaInicioTexto = txtHoraInicio.getText().trim();
        String horaFinTexto = txtHoraFin.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();

        if (nombre.isEmpty()
                || descripcion.isEmpty()
                || fecha == null
                || horaInicioTexto.isEmpty()
                || horaFinTexto.isEmpty()
                || ubicacion.isEmpty()) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Campos incompletos",
                    "Complete todos los campos de la actividad."
            );
            return;
        }

        LocalTime horaInicio;
        LocalTime horaFin;

        try {
            horaInicio = parsearHoraInteligente(horaInicioTexto);
            horaFin = parsearHoraInteligente(horaFinTexto);

        } catch (Exception e) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Hora inválida",
                    "Use un formato válido, por ejemplo: "
                    + "14, 14:30 o 14:30:00."
            );
            return;
        }

        if (!horaFin.isAfter(horaInicio)) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Horario incorrecto",
                    "La hora de finalización debe ser posterior "
                    + "a la hora de inicio."
            );
            return;
        }

        int capacidadMax;

        try {
            capacidadMax = Integer.parseInt(
                    spnCapacidadMax.getEditor().getText().trim()
            );

            if (capacidadMax < 1 || capacidadMax > 500) {
                throw new NumberFormatException();
            }

            spnCapacidadMax.getValueFactory().setValue(capacidadMax);

        } catch (NumberFormatException e) {
            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Capacidad inválida",
                    "La capacidad debe ser un número entre 1 y 500."
            );
            return;
        }

        String sql = "INSERT INTO actividad "
                + "(nombre, descripcion, fecha, hora_inicio, "
                + "hora_finalizacion, ubicacion, capacidad_max, "
                + "estado, evento_id, expositor_id, tipo_actividad_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 'abierta', 1, 1, 1)";

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

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Actividad registrada",
                    "La actividad se guardó correctamente."
            );

            limpiarCampos();
            cargarDatosTabla();

        } catch (Exception e) {
            e.printStackTrace();

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error al guardar",
                    "No se pudo registrar la actividad: "
                    + e.getMessage()
            );
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        dpFecha.setValue(null);
        txtHoraInicio.clear();
        txtHoraFin.clear();
        txtUbicacion.clear();

        spnCapacidadMax.getValueFactory().setValue(30);
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void regresarPrincipal(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/app/principal.xml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) btnVolver.getScene().getWindow();
            boolean maximizada = stage.isMaximized();

            stage.setScene(new Scene(root));
            stage.setTitle("Gestor de Eventos");
            stage.setMaximized(maximizada);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error de navegación",
                    "No se pudo regresar al menú: "
                    + e.getMessage()
            );
        }
    }
}
