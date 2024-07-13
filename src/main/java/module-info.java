module com.example.journal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.journal to javafx.fxml, java.sql;
    exports com.example.journal;
}