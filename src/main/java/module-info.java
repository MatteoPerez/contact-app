module isen.project.contact_app {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

    opens isen.project.contact_app to javafx.fxml;
    exports isen.project.contact_app;
}
