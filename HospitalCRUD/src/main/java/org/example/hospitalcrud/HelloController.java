package org.example.hospitalcrud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class HelloController {

    @FXML private TextField txtId;
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

        colId.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getId()));

        colNombre.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));

        colEdad.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getEdad()));

        colEnfermedad.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getEnfermedad()));

        colTelefono.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTelefono()));

        mostrarPacientes();
    }
    
    @FXML
    public void agregarPaciente() {

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
            e.printStackTrace();
        }
    }


    public void mostrarPacientes() {

        lista.clear();

        String sql = "SELECT * FROM pacientes";

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

        String sql = "UPDATE pacientes SET nombre=?, edad=?, enfermedad=?, telefono=? WHERE id=?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText());
            ps.setInt(2, Integer.parseInt(txtEdad.getText()));
            ps.setString(3, txtEnfermedad.getText());
            ps.setString(4, txtTelefono.getText());
            ps.setInt(5, Integer.parseInt(txtId.getText()));

            ps.executeUpdate();

            mostrarPacientes();
            limpiar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void eliminar() {

        String sql = "DELETE FROM pacientes WHERE id=?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtId.getText()));

            ps.executeUpdate();

            mostrarPacientes();
            limpiar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void limpiar() {
        txtId.clear();
        txtNombre.clear();
        txtEdad.clear();
        txtEnfermedad.clear();
        txtTelefono.clear();
    }
}