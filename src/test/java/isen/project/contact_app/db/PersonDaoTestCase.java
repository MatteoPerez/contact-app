package isen.project.contact_app.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import isen.project.contact_app.db.DataSourceFactory;
import isen.project.contact_app.db.PersonDao;
import isen.project.contact_app.db.Person;

public class PersonDaoTestCase {
	private PersonDao personDao = new PersonDao();

	@BeforeEach
	public void initDatabase() throws Exception {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(
				"CREATE TABLE IF NOT EXISTS person (\r\n"
				+ "  idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n" + "  lastname VARCHAR(45) NOT NULL,\r\n"
				+ "  firstname VARCHAR(45) NOT NULL,\r\n" + "  nickname VARCHAR(45) NOT NULL,\r\n" + "  phone_number VARCHAR(15) NULL,\r\n"
				+ "  address VARCHAR(200) NULL,\r\n" + "  email_address VARCHAR(150) NULL,\r\n"
				+ "  birth_date DATE NULL);");
		stmt.executeUpdate("DELETE FROM person");
		stmt.executeUpdate("DELETE FROM sqlite_sequence WHERE name='person'");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) VALUES (1,'Genre','Elodie','UwU','06 25 26 08 74','Japon','e@gmail.com','2015-12-21')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) VALUES (2,'Meuniez','Raphael','Le GOAT','06 30 28 09 47','Italie','r@gmail.com','2000-05-26')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) VALUES (3,'Perez','Matteo','...','06 30 26 81 30','Espagne','m@gmail.com','2003-01-02')");
		stmt.close();
		connection.close();
	}

	@Test
	public void shouldListPerson() {
		// WHEN
		List<Person> persons = personDao.listPersons();
		// THEN
		assertThat(persons).hasSize(3);
		assertThat(persons).extracting("id", "lastName", "firstName", "nickName", "phoneNumber", "address", "emailAddress", "birthDate").containsOnly(
				tuple(1, "Genre", "Elodie", "UwU", "06 25 26 08 74", "Japon", "e@gmail.com", Date.valueOf(LocalDate.parse("2015-12-21"))),
				tuple(2, "Meuniez", "Raphael", "Le GOAT", "06 30 28 09 47", "Italie", "r@gmail.com", Date.valueOf(LocalDate.parse("2000-05-26"))),
				tuple(3, "Perez", "Matteo", "...", "06 30 26 81 30", "Espagne", "m@gmail.com", Date.valueOf(LocalDate.parse("2003-01-02"))));
	}
	
//	@Test
//	public void shouldGetPersonByName() {
//		// WHEN
//		Person person = personDao.getPerson("Comedy");
//		// THEN
//		assertThat(person.getId()).isEqualTo(2);
//		assertThat(person.getName()).isEqualTo("Comedy");
//	}
//	
//	@Test
//	public void shouldNotGetUnknownPerson() {
//		// WHEN
//		Person person = personDao.getPerson("Unknown");
//		// THEN
//		assertThat(person).isNull();
//	}
//	
//	@Test
//	public void shouldAddPerson() throws Exception {
//		// WHEN 
//		personDao.addPerson("Western");
//		// THEN
//		Connection connection = DataSourceFactory.getDataSource().getConnection();
//		Statement statement = connection.createStatement();
//		ResultSet resultSet = statement.executeQuery("SELECT * FROM genre WHERE name='Western'");
//		assertThat(resultSet.next()).isTrue();
//		assertThat(resultSet.getInt("idgenre")).isNotNull();
//		assertThat(resultSet.getString("name")).isEqualTo("Western");
//		assertThat(resultSet.next()).isFalse();
//		resultSet.close();
//		statement.close();
//		connection.close();
//	}
}
