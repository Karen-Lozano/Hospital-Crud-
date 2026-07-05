package org.example.hospitalcrud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class HelloController {

    // Cambiamos txtId a una variable normal si no tienes un TextField físico en el FXML
    // O si lo tienes oculto, déjalo con @FXML. Aquí lo manejaremos seguro.
    private int idSeleccionado = -1;

    @FXML private TextField txtNombre;
    @FXML private TextField txtEdad;
    @FXML private TextField txtEnfermedad;
    @FXML private TextField txtTelefono;

    @FXML private TableView<Paciente> tablaPacientes;

    @FXML private TableColumn<Paciente, Integer> colId;
    @FXML private TableColumn<Paciente, String> colNombre;
    @FXML private TableColumn<Paciente, Integer> colEdad;
    @FXML private TableColumn<Paciente, String> colEnfermedad;
    @FXML private TableColumn<Paciente, String> colTelefono;

    ObservableList<Paciente> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));
        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));
        colEdad.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getEdad()));
        colEnfermedad.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEnfermedad()));
        colTelefono.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));

        mostrarPacientes();

        // ESCUCHADOR: Al hacer clic en una fila de la tabla, llena los campos de texto
        tablaPacientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idSeleccionado = newSelection.getId(); // Guardamos el ID del paciente seleccionado
                txtNombre.setText(newSelection.getNombre());
                txtEdad.setText(String.valueOf(newSelection.getEdad()));
                txtEnfermedad.setText(newSelection.getEnfermedad());
                txtTelefono.setText(newSelection.getTelefono());
            }
        });
    }

    @FXML
    public void agregar() {
        String sql = "INSERT INTO pacientes(nombre, edad, enfermedad, telefono) VALUES (?,?,?,?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText());
            ps.setInt(2, Integer.parseInt(txtEdad.getText()));
            ps.setString(3, txtEnfermedad.getText());
            ps.setString(4, txtTelefono.getText());

            ps.executeUpdate();
            mostrarPacientes();
            limpiar();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo agregar el paciente.");
            e.printStackTrace();
        }
    }

    public void mostrarPacientes() {
        lista.clear();
        String sql = "SELECT * FROM pacientes ORDER BY id ASC"; // Ordenados por ID
        try (Connection con = Conexion.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("enfermedad"),
                        rs.getString("telefono")
                ));
            }
            tablaPacientes.setItems(lista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void actualizar() {
        if (idSeleccionado == -1) {
            mostrarAlerta("Atención", "Por favor, selecciona un paciente de la tabla para modificar.");
            return;
        }

        String sql = "UPDATE pacientes SET nombre=?, edad=?, enfermedad=?, telefono=? WHERE id=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText());
            ps.setInt(2, Integer.parseInt(txtEdad.getText()));
            ps.setString(3, txtEnfermedad.getText());
            ps.setString(4, txtTelefono.getText());
            ps.setInt(5, idSeleccionado);

            ps.executeUpdate();
            mostrarPacientes();
            limpiar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void eliminar() {
        if (idSeleccionado == -1) {
            mostrarAlerta("Atención", "Por favor, selecciona un paciente de la tabla para eliminar.");
            return;
        }

        String sql = "DELETE FROM pacientes WHERE id=?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSeleccionado);

            ps.executeUpdate();
            mostrarPacientes();
            limpiar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void limpiar() {
        idSeleccionado = -1; // Resetear ID seleccionado
        txtNombre.clear();
        txtEdad.clear();
        txtEnfermedad.clear();
        txtTelefono.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}