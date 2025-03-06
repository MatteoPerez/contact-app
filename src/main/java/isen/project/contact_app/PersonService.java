package isen.project.contact_app;

import isen.project.contact_app.db.Person;
import isen.project.contact_app.db.PersonDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonService {
	private ObservableList<Person> persons;
	private PersonDao personDao = new PersonDao();
	
	private PersonService() {
		persons = FXCollections.observableArrayList();
		for (Person person : personDao.listPersons()) {
			System.out.println(person);
			persons.add(person);
		}
	}

	public static ObservableList<Person> getPersons() {
		return QuestionServiceHolder.INSTANCE.persons;
	}

	public static void addQuestion(Person person) {
		QuestionServiceHolder.INSTANCE.persons.add(person);
	}

	private static class QuestionServiceHolder {
		private static final PersonService INSTANCE = new PersonService();
	}
}
