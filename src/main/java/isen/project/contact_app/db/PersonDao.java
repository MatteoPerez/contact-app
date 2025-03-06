package isen.project.contact_app.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {
	
	public List<Person> listPersons() {
		List<Person> listOfPersons = new ArrayList<>();
		
		try(Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			try(Statement statement = connection.createStatement();) {
				try(ResultSet results = statement.executeQuery("SELECT * FROM person")) {
					while(results.next()) {
						Person people = new Person(
								results.getInt("idperson"), 
								results.getString("lastname"),
								results.getString("firstname"),
								results.getString("nickname"),
								results.getString("phone_number"),
								results.getString("address"),
								results.getString("email_address"),
								Date.valueOf(results.getString("birth_date")));
						listOfPersons.add(people);
					}
					return listOfPersons;
				}
			}
		} catch(SQLException e) {
			// Manage Exception
	        e.printStackTrace();
	        return null;
		}
	}

	public Person getPersonById(int id) {
		try(Connection connection = DataSourceFactory.getDataSource().getConnection();) {
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE idperson = ?")) {
				statement.setInt(1, id);
				try(ResultSet results = statement.executeQuery()) {
					if(results.next()) {
						return new Person(
								results.getInt("idperson"), 
								results.getString("lastname"),
								results.getString("firstname"),
								results.getString("nickname"),
								results.getString("phone_number"),
								results.getString("address"),
								results.getString("email_address"),
								Date.valueOf(results.getString("birth_date")));
					} else {
						return null;
					}
				}
			}
		} catch(SQLException e) {
			// Manage Exception
	        e.printStackTrace();
	        return null;
		}
	}
	
	public Person addPerson(Person person) {
		try(Connection connection = DataSourceFactory.getDataSource().getConnection();) {
			String sqlQuery = "INSERT INTO person (lastname,firstname,nickname,phone_number,address,email_address,birth_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
			try(PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, person.getLastName());
				statement.setString(2, person.getFirstName());
				statement.setString(3, person.getNickName());
				statement.setString(4, person.getPhoneNumber());
				statement.setString(5, person.getAddress());
				statement.setString(6, person.getEmailAddress());
	            statement.setDate(7, person.getBirthDate());
	            statement.executeUpdate();
	            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            	if (generatedKeys.next()) {
	            		person.setId(generatedKeys.getInt(1));
	            	}
	            	return person;
	            }
			}
		} catch(SQLException e) {
			// Manage Exception
	        e.printStackTrace();
	        return null;
		}
	}
	
	public void deletePerson(Person person) {
		try(Connection connection = DataSourceFactory.getDataSource().getConnection();) {
			String sqlQuery = "DELETE FROM person WHERE idperson = ?";
			try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
				statement.setInt(1, person.getId());
	            statement.executeUpdate();
			}
		} catch(SQLException e) {
			// Manage Exception
	        e.printStackTrace();
		}
	}
}
