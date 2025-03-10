package isen.project.contact_app.view;

import java.io.IOException;

import isen.project.contact_app.App;
import isen.project.contact_app.db.Person;
import isen.project.contact_app.db.PersonDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

public class ContactCardController {
	
	private HomeController homeController;
	
	private Person person;
	
    //@FXML private ImageView profileImage;
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private Label phoneNumber;
    
    @FXML private Button button;

    @FXML
    public void initialize() {
        this.button.getStyleClass().add("contact-button");
    }
    
    public void setContactPerson(Person p) {
    	this.person = p;
    }

    public void setContactData() {
        firstName.setText(this.person.getFirstName());
        lastName.setText(this.person.getLastName());
        //if (imagePath != null && !imagePath.isEmpty()) {
        //    profileImage.setImage(new Image(imagePath));
        //}
    }
    
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    
    public void clickOnContactCard() {
    	this.homeController.profilCard.visibleProperty().set(true);
    	fillProfilInfo();
    }
    
    public void fillProfilInfo() {
    	this.homeController.nom.setText(this.person.getLastName());
    	this.homeController.prenom.setText(this.person.getFirstName());
    	this.homeController.nickname.setText(this.person.getNickName());
    	this.homeController.adress.setText(this.person.getAddress());
    	this.homeController.number.setText(this.person.getPhoneNumber());
    	this.homeController.email.setText(this.person.getEmailAddress());
    	this.homeController.setSelectedPerson(this.person);
    }
}
