package isen.project.contact_app.view;

import java.sql.Date;

import isen.project.contact_app.PersonService;
import isen.project.contact_app.PersonValueFactory;
import isen.project.contact_app.db.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TestController {
	@FXML
	public TableView<Person> personsTable;
	@FXML
	public TableColumn<Person,String> personColumn;
	@FXML
	public AnchorPane formPane;
	@FXML
	public TextField firstNameField;
	@FXML
	public TextField lastNameField;
	@FXML
	public TextField nickNameField;
	@FXML
	public TextField phoneNumberField;
	@FXML
	public TextField addressField;
	@FXML
	public TextField emailAddressField;
	@FXML
	public DatePicker birthDateField;
	
	private Person currentPerson;
	
    @FXML
    private void handleSaveButton() {
    	currentPerson.setLastName(lastNameField.getText());
    	currentPerson.setFirstName(firstNameField.getText());
    	currentPerson.setNickName(nickNameField.getText());
    	currentPerson.setPhoneNumber(phoneNumberField.getText());
    	currentPerson.setAddress(addressField.getText());
    	currentPerson.setEmailAddress(emailAddressField.getText());
    	if(birthDateField.getValue() != null) {
    		currentPerson.setBirthDate(Date.valueOf(birthDateField.getValue()));
    	} else {
    		currentPerson.setBirthDate(null);
    	}
    	
    	Person newPerson = new Person();
    	newPerson.setId(currentPerson.getId());
    	newPerson.setLastName(lastNameField.getText());
    	newPerson.setFirstName(firstNameField.getText());
    	newPerson.setNickName(nickNameField.getText());
    	newPerson.setPhoneNumber(phoneNumberField.getText());
    	newPerson.setAddress(addressField.getText());
    	newPerson.setEmailAddress(emailAddressField.getText());
    	if(birthDateField.getValue() != null) {
    		newPerson.setBirthDate(Date.valueOf(birthDateField.getValue()));
    	} else {
    		newPerson.setBirthDate(null);
    	}
    	PersonService.updatePerson(newPerson);
    }


    @FXML
    private void handleNewButton() {
    	Person newPerson = new Person();
    	PersonService.addPerson(newPerson);
    	personsTable.getSelectionModel().select(newPerson);
    }


    @FXML
    private void handleDeleteButton() {
    	PersonService.removePerson(personsTable.getSelectionModel().getSelectedItem());
    	resetView();
    }
    
    public void refreshList() {
    	personsTable.refresh();
    	personsTable.getSelectionModel().clearSelection();
    }
    
    public void populateList() {
    	personsTable.setItems(PersonService.getPersons());
    	refreshList();
    }
    
    @FXML
	private void initialize() {
		PersonValueFactory questionValueFactory = new PersonValueFactory();
		personColumn.setCellValueFactory(questionValueFactory);
		populateList();
		personsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
			@Override
			public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
				showPersonDetails(newValue);
			}
		});
		resetView();
	}
    
    public void showPersonDetails(Person person) {
    	if (person == null) {
    		formPane.setVisible(false);
    	} else {
    		formPane.setVisible(true);
    		this.currentPerson = person;
    		lastNameField.setText(currentPerson.getLastName());
    		firstNameField.setText(currentPerson.getFirstName());
    		nickNameField.setText(currentPerson.getNickName());
    		phoneNumberField.setText(currentPerson.getPhoneNumber());
    		addressField.setText(currentPerson.getAddress());
    		emailAddressField.setText(currentPerson.getEmailAddress());
    		if (currentPerson.getBirthDate() != null) {
                birthDateField.setValue(currentPerson.getBirthDate().toLocalDate());
            } else {
                birthDateField.setValue(null);
            }
    	}
    }
    
    public void resetView() {
    	showPersonDetails(null);
    	refreshList();
    }
}
