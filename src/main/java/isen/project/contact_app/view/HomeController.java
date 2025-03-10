package isen.project.contact_app.view;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import isen.project.contact_app.App;
import isen.project.contact_app.db.Person;
import isen.project.contact_app.db.PersonDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeController {

    @FXML private FlowPane contactsContainer;
    @FXML public AnchorPane profilCard;
    @FXML public AnchorPane editCard;
    
    @FXML public Text nom;
    @FXML public Text prenom;
    @FXML public Text nickname;
    @FXML public Text adress;
    @FXML public Text number;
    @FXML public Text email;
    @FXML public Text birthDate;
    
    @FXML public TextField editnom;
    @FXML public TextField editprenom;
    @FXML public TextField editnickname;
    @FXML public TextField editadress;
    @FXML public TextField editnumber;
    @FXML public TextField editemail;
    @FXML public DatePicker editbirthDate;
    
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button okButton;
    @FXML private Button addButton;
    @FXML private Button cancelButton;
    
    private PersonDao personDAO = new PersonDao();
    private Person selectedPerson; // Pour suivre la personne sélectionnée

    public void initialize() {
        updatePersonList();
    }
    
    public void updatePersonList() {
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

    public void setVisibleProfilCard(Person person) {
        this.selectedPerson = person;
        // Mise à jour des informations affichées
        if (selectedPerson != null) {
            nom.setText(person.getLastName());
            prenom.setText(person.getFirstName());
            nickname.setText(person.getNickName());
            adress.setText(person.getAddress());
            number.setText(person.getPhoneNumber());
            email.setText(person.getEmailAddress());
            Date date = person.getBirthDate();
            String birthDateString;
            if (date != null) {
                birthDateString = dateToString(date,"yyyy-MM-dd");
            } else {
                birthDateString = "";
            }
            birthDate.setText(birthDateString);
        }
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
    
    public void onEditClicked() {
    	profilCard.visibleProperty().set(false);
    	editCard.visibleProperty().set(true);
    	fillEditInfo();
    }
    
    public void fillEditInfo() {
    	this.editnom.setPromptText(this.selectedPerson.getLastName());
        this.editprenom.setPromptText(this.selectedPerson.getFirstName()); // Correction ici
        this.editnickname.setPromptText(this.selectedPerson.getNickName());
        this.editadress.setPromptText(this.selectedPerson.getAddress());
        this.editnumber.setPromptText(this.selectedPerson.getPhoneNumber());
        this.editemail.setPromptText(this.selectedPerson.getEmailAddress());
    }
    
    @FXML
public void onOkClicked() {
    if (this.selectedPerson != null) {
        // Mise à jour d'une personne existante
        String newLastName = editnom.getText().isEmpty() ? selectedPerson.getLastName() : editnom.getText();
        String newFirstName = editprenom.getText().isEmpty() ? selectedPerson.getFirstName() : editprenom.getText();
        String newNickName = editnickname.getText().isEmpty() ? selectedPerson.getNickName() : editnickname.getText();
        String newAddress = editadress.getText().isEmpty() ? selectedPerson.getAddress() : editadress.getText();
        String newPhoneNumber = editnumber.getText().isEmpty() ? selectedPerson.getPhoneNumber() : editnumber.getText();
        String newEmailAddress = editemail.getText().isEmpty() ? selectedPerson.getEmailAddress() : editemail.getText();

        // Récupérer la date de naissance depuis le DatePicker
        Date birthDate = null;
        if (editbirthDate.getValue() != null) {
            birthDate = Date.valueOf(editbirthDate.getValue());
        }

        Person updatedPerson = new Person(
                selectedPerson.getId(),
                newLastName,
                newFirstName,
                newNickName,
                newPhoneNumber,
                newAddress,
                newEmailAddress,
                birthDate
        );
        personDAO.updatePerson(updatedPerson);

        // Mettre à jour la liste des contacts
        contactsContainer.getChildren().clear(); // Nettoie la liste
        updatePersonList(); // Recharge la liste mise à jour

        // Mettre à jour la carte du profil
        setVisibleProfilCard(updatedPerson);
    } else {
        // Ajout d'une nouvelle personne (logique déjà correcte)
        String newLastName = editnom.getText().isEmpty() ? null : editnom.getText();
        String newFirstName = editprenom.getText().isEmpty() ? null : editprenom.getText();
        String newNickName = editnickname.getText().isEmpty() ? null : editnickname.getText();
        String newAddress = editadress.getText().isEmpty() ? null : editadress.getText();
        String newPhoneNumber = editnumber.getText().isEmpty() ? null : editnumber.getText();
        String newEmailAddress = editemail.getText().isEmpty() ? null : editemail.getText();

        // Récupérer la date de naissance depuis le DatePicker pour une nouvelle personne
        java.sql.Date birthDate = null;
        if (editbirthDate.getValue() != null) {
            birthDate = java.sql.Date.valueOf(editbirthDate.getValue());
        }

        Person newPerson = new Person(
                newLastName,
                newFirstName,
                newNickName,
                newPhoneNumber,
                newAddress,
                newEmailAddress,
                birthDate
        );

        Person addedPerson = personDAO.addPerson(newPerson);
        // Ajout de la nouvelle personne directement à l'interface sans recharger toute la liste
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactCard.fxml"));
        try {
            AnchorPane contactCard = loader.load();
            ContactCardController newContactCard = loader.getController();
            newContactCard.setContactPerson(addedPerson);
            newContactCard.setContactData();
            newContactCard.setHomeController(this);
            contactsContainer.getChildren().add(contactCard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Retour à la vue du profil après modification ou ajout
    editCard.visibleProperty().set(false);
    profilCard.visibleProperty().set(true);
    clearEditCard();
}
    public void onNewContactClicked() {
    	clearEditCard();
    	this.selectedPerson = null;
    	this.editCard.visibleProperty().set(true);
    	
    }
    
    public void clearEditCard() {
    	// Réinitialisation des champs de saisie
        editnom.clear();
        editprenom.clear();
        editnickname.clear();
        editadress.clear();
        editnumber.clear();
        editemail.clear();
        editbirthDate.setValue(null);
        editnom.promptTextProperty().set("Last Name");
        editprenom.promptTextProperty().set("First Name");
        editnickname.promptTextProperty().set("Nick Name");
        editadress.promptTextProperty().set("Address");
        editnumber.promptTextProperty().set("Phone Number");
        editemail.promptTextProperty().set("Email Address");
    }
    
    public static String dateToString(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
    
    public void onCancelClicked() {
    	clearEditCard();
    	this.selectedPerson = null;
    	this.profilCard.visibleProperty().set(false);
    	this.editCard.visibleProperty().set(false);
    }
}
