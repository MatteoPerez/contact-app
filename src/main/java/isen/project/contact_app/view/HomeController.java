package isen.project.contact_app.view;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import isen.project.contact_app.App;
import isen.project.contact_app.db.Person;
import isen.project.contact_app.db.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class HomeController {
	
	@FXML private FlowPane contactsContainer;
	
	public void initialize() {
		PersonDao personDAO = new PersonDao();
        List<Person> contacts = personDAO.listPersons();
        for (Person person : contacts) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactCard.fxml"));
                AnchorPane contactCard = loader.load();

                ContactCardController newContactCard = loader.getController();
                newContactCard.setContactPerson(person);
                newContactCard.setContactData();

                contactsContainer.getChildren().add(contactCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
    @FXML
    private void switchToListPersons() throws IOException {
    	App.setRoot("/isen/project/contact_app/view/ListPersonsScreen");
    }
    
    @FXML
    private void switchToAddPersons() throws IOException {
    	App.setRoot("/isen/project/contact_app/view/AddPersonScreen");
    }
}
