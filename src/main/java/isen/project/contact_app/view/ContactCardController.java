package isen.project.contact_app.view;

import isen.project.contact_app.db.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;

public class ContactCardController {
	
	private Person person;
	
    //@FXML private ImageView profileImage;
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private Label phoneNumber;
    @FXML private Label emailLabel;
    
    public void setContactPerson(Person p) {
    	this.person = p;
    }

    public void setContactData() {
        firstName.setText(this.person.getFirstName());
        lastName.setText(this.person.getLastName());
        emailLabel.setText(this.person.getEmailAddress());
        //if (imagePath != null && !imagePath.isEmpty()) {
        //    profileImage.setImage(new Image(imagePath));
        //}
    }
}
