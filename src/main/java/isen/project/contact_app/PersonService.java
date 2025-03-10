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
			persons.add(person);
		}
	}

	public static ObservableList<Person> getPersons() {
		return PersonServiceHolder.INSTANCE.persons;
	}

	public static void addPerson(Person person) {
		PersonServiceHolder.INSTANCE.persons.add(person);
		PersonServiceHolder.INSTANCE.personDao.addPerson(person);
	}
	
	public static void removePerson(Person person) {
		PersonServiceHolder.INSTANCE.persons.remove(person);
		PersonServiceHolder.INSTANCE.personDao.deletePerson(person);
	}
	
	public static void updatePerson(Person person) {
	    ObservableList<Person> persons = PersonServiceHolder.INSTANCE.persons;
	    for (int i = 0; i < persons.size(); i++) {
	        if (persons.get(i).getId().equals(person.getId())) {
	            persons.set(i, person);
	            break;
	        }
	    }
	    PersonServiceHolder.INSTANCE.personDao.updatePerson(person);
	}


	private static class PersonServiceHolder {
		private static final PersonService INSTANCE = new PersonService();
	}
}
