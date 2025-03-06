package isen.project.contact_app.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) VALUES (1,'Genre','Elodie','UwU','06 51 98 92 42','Japon','e@gmail.com','2003-05-29')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) VALUES (2,'Meuniez','Raphael','Le GOAT','06 30 28 09 47','Italie','r@gmail.com','2002-01-01')");
		stmt.executeUpdate("INSERT INTO person(idperson,lastname,firstname,nickname,phone_number,address,email_address,birth_date) VALUES (3,'Perez','Matteo','...','06 30 26 81 30','Espagne','m@gmail.com','2003-06-17')");
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
				tuple(1, "Genre", "Elodie", "UwU", "06 51 98 92 42", "Japon", "e@gmail.com", Date.valueOf(LocalDate.parse("2003-05-29"))),
				tuple(2, "Meuniez", "Raphael", "Le GOAT", "06 30 28 09 47", "Italie", "r@gmail.com", Date.valueOf(LocalDate.parse("2002-01-01"))),
				tuple(3, "Perez", "Matteo", "...", "06 30 26 81 30", "Espagne", "m@gmail.com", Date.valueOf(LocalDate.parse("2003-06-17"))));
	}
	
	@Test
	public void shouldGetPersonById() {
		// WHEN
		Person person = personDao.getPersonById(2);
		// THEN
		assertThat(person.getId()).isEqualTo(2);
		assertThat(person.getFirstName()).isEqualTo("Raphael");
	}
	
//	@Test
//	public void shouldNotGetUnknownPerson() {
//		// WHEN
//		Person person = personDao.getPerson("Unknown");
//		// THEN
//		assertThat(person).isNull();
//	}
	
	@Test
	public void shouldDeletePerson() throws Exception {		
		// WHEN
		assertThat(personDao.getPersonById(2)).isNotNull();
		personDao.deletePerson(personDao.getPersonById(2));
		// THEN
		assertThat(personDao.getPersonById(2)).isNull();
		List<Person> persons = personDao.listPersons();
	    assertThat(persons).hasSize(2);
	}
	
	@Test
	public void shouldAddPerson() throws Exception {
		// WHEN
		Person person = new Person("Fisher", "Sam", "Splinter Cell", "06 06 06 06 06", "USA", "s@gmail.com", Date.valueOf(LocalDate.parse("1975-10-25")));
		personDao.addPerson(person);
		// THEN		
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE lastname = ?");
		statement.setString(1, "Fisher");
		ResultSet resultSet = statement.executeQuery();
		
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idperson")).isNotNull();
		assertThat(resultSet.getString("lastname")).isEqualTo("Fisher");
		assertThat(resultSet.getString("firstname")).isEqualTo("Sam");
		assertThat(resultSet.getString("nickname")).isEqualTo("Splinter Cell");
		assertThat(resultSet.getString("phone_number")).isEqualTo("06 06 06 06 06");
		assertThat(resultSet.getString("address")).isEqualTo("USA");
		assertThat(resultSet.getString("email_address")).isEqualTo("s@gmail.com");
		assertThat(resultSet.getDate("birth_date")).isEqualTo(Date.valueOf(LocalDate.parse("1975-10-25")));
		assertThat(resultSet.next()).isFalse();
		
		resultSet.close();
		statement.close();
		connection.close();
	}
}
