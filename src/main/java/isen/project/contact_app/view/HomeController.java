package isen.project.contact_app.view;

import java.io.IOException;

import isen.project.contact_app.App;
import javafx.fxml.FXML;

public class HomeController {
    @FXML
    private void switchToListPersons() throws IOException {
    	App.setRoot("/isen/project/contact_app/view/ListPersonsScreen");
    }
    
    @FXML
    private void switchToAddPersons() throws IOException {
    	App.setRoot("/isen/project/contact_app/view/AddPersonScreen");
    }
}
