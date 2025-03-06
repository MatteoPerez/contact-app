module isen.project.contact_app {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires sqlite.jdbc;
	requires javafx.graphics;

    opens isen.project.contact_app to javafx.fxml;
    opens isen.project.contact_app.view to javafx.fxml;
    opens isen.project.contact_app.db to javafx.base;
    exports isen.project.contact_app;
}
