module org.example.hospitalcrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.hospitalcrud to javafx.fxml;
    exports org.example.hospitalcrud;
}