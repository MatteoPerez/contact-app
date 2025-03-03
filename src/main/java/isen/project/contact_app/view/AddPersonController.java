package isen.project.contact_app.view;

import java.io.IOException;

import isen.project.contact_app.App;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddPersonController {
	@FXML
	private TextField firstNameLabel;
	@FXML
	private TextField lastNameLabel;
	@FXML
	private TextField nickNameLabel;
	@FXML
	private TextField phoneNumberLabel;
	@FXML
	private TextField adressLabel;
	@FXML
	private TextField emailAdressLabel;
	@FXML
	private DatePicker birthDatePicker;
	
    @FXML
    private void switchToListPersons() throws IOException {
    	App.setRoot("/isen/project/contact_app/view/ListPersonsScreen");
    }
    
    @FXML
    private void switchToHome() throws IOException {
    	App.setRoot("/isen/project/contact_app/view/HomeScreen");
    }
    
    @FXML
    private void addPerson() throws IOException {
    	String firstNameText = firstNameLabel.getText();
    	System.out.println("Ouaip ouaip OUAIP !! " + firstNameText);
    }
}
