package isen.project.contact_app.view;

import isen.project.contact_app.PersonService;
import isen.project.contact_app.PersonValueFactory;
import isen.project.contact_app.db.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
	
//    @FXML
//    private void handleSaveButton() {
//    	currentQuestion.setQuestion(questionField.getText());
//    	currentQuestion.getAnswer1().setText(answer1Field.getText());
//    	currentQuestion.getAnswer2().setText(answer2Field.getText());
//    	currentQuestion.getAnswer3().setText(answer3Field.getText());
//    	if (radio1.isSelected()) {
//    		currentQuestion.getAnswer1().setGoodAnswer(true);
//    		currentQuestion.getAnswer2().setGoodAnswer(false);
//    		currentQuestion.getAnswer3().setGoodAnswer(false);
//    	}
//    	if (radio2.isSelected()) {
//    		currentQuestion.getAnswer1().setGoodAnswer(false);
//    		currentQuestion.getAnswer2().setGoodAnswer(true);
//    		currentQuestion.getAnswer3().setGoodAnswer(false);
//    	}
//    	if (radio3.isSelected()) {
//    		currentQuestion.getAnswer1().setGoodAnswer(false);
//    		currentQuestion.getAnswer2().setGoodAnswer(false);
//    		currentQuestion.getAnswer3().setGoodAnswer(true);
//    	}
//    	resetView();
//    }


//    @FXML
//    private void handleNewButton() {
//    	Question newQuestion = new Question();
//    	QuestionService.addQuestion(newQuestion);
//    	questionsTable.getSelectionModel().select(newQuestion);
//    }


//    @FXML
//    private void handleDeleteButton() {
//    	int selectedIndex = questionsTable.getSelectionModel().getSelectedIndex();
//        if (selectedIndex >= 0) {
//            questionsTable.getItems().remove(selectedIndex);
//            resetView();
//        }
//    }
    
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
//		personsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
//			@Override
//			public void changed(ObservableValue<? extends Person> observable, Person oldValue, Person newValue) {
//				showQuestionDetails(newValue);
//			}
//		});
//		resetView();
	}
    
//    public void showQuestionDetails(Question question) {
//    	if (question == null) {
//    		formPane.setVisible(false);
//    	} else {
//    		formPane.setVisible(true);
//    		this.currentQuestion = question;
//    		questionField.setText(currentQuestion.getQuestion());
//    		answer1Field.setText(currentQuestion.getAnswer1().getText());
//    		answer2Field.setText(currentQuestion.getAnswer2().getText());
//    		answer3Field.setText(currentQuestion.getAnswer3().getText());
//    		radio1.setSelected(currentQuestion.getAnswer1().isGoodAnswer());
//    		radio2.setSelected(currentQuestion.getAnswer2().isGoodAnswer());
//    		radio3.setSelected(currentQuestion.getAnswer3().isGoodAnswer());
//    	}
//    }
    
//    public void resetView() {
//    	showQuestionDetails(null);
//    	refreshList();
//    }
}
