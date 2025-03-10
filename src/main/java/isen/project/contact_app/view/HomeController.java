package isen.project.contact_app.view;

import java.io.IOException;
import java.util.List;

import isen.project.contact_app.App;
import isen.project.contact_app.db.Person;
import isen.project.contact_app.db.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController {

    @FXML private Button addButton;
    @FXML private FlowPane contactsContainer;
    @FXML public AnchorPane profilCard;
    
    @FXML public Text nom;
    @FXML public Text prenom;
    @FXML public Text nickname;
    @FXML public Text adress;
    @FXML public Text number;
    @FXML public Text email;
    @FXML public Text birthDate;
    
    @FXML private Button deleteButton;
    
    private PersonDao personDAO = new PersonDao();
    private Person selectedPerson; // Pour suivre la personne sélectionnée

    public void initialize() {
        List<Person> contacts = personDAO.listPersons();
        for (Person person : contacts) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactCard.fxml"));
                AnchorPane contactCard = loader.load();

                ContactCardController newContactCard = loader.getController();
                newContactCard.setContactPerson(person);
                newContactCard.setContactData();
                newContactCard.setHomeController(this);

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
    private void AddButtonAction() throws IOException {
        App.setRoot("/isen/project/contact_app/view/AddPersonScreen");
    }

    public void setVisibleProfilCard(Person person) {
        this.selectedPerson = person;
        this.profilCard.visibleProperty().set(true);

        // Mise à jour des informations affichées
        nom.setText(person.getLastName());
        prenom.setText(person.getFirstName());
        nickname.setText(person.getNickName());
        adress.setText(person.getAddress());
        number.setText(person.getPhoneNumber());
        email.setText(person.getEmailAddress());
        birthDate.setText(person.getBirthDate().toString());
    }

    public void openDeletePopup() {
        if (selectedPerson == null) {
            return; // Si aucune personne sélectionnée, ne rien faire
        }

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Confirm Deletion");

        Text message = new Text("Are you sure you want to delete " + selectedPerson.getFirstName() + "?");
        message.setStyle("-fx-fill: white; -fx-font-size: 14px;");

        // Bouton DELETE
        Button deleteButton = new Button("DELETE");
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        deleteButton.setOnAction(event -> {
            personDAO.deletePerson(selectedPerson);
            contactsContainer.getChildren().clear(); // Nettoie la liste
            initialize(); // Recharge la liste mise à jour
            popupStage.close();
            profilCard.visibleProperty().set(false); // Cache la carte du profil
        });

        // Bouton CANCEL
        Button cancelButton = new Button("CANCEL");
        cancelButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
        cancelButton.setOnAction(event -> popupStage.close());

        VBox popupRoot = new VBox(15, message, deleteButton, cancelButton);
        popupRoot.setStyle("-fx-background-color: #67448f; -fx-padding: 20px; -fx-alignment: center;");

        Scene popupScene = new Scene(popupRoot, 300, 150);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    @FXML
    public void onDeleteClicked() {
        openDeletePopup();
    }
    
    public void setSelectedPerson(Person p) {
    	this.selectedPerson = p;
    }
}
