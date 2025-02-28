package isen.project.contact_app.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {
	
	public List<Person> listPeoples() {
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
								results.getString("adress"),
								results.getString("email_adress"),
								results.getDate("birth_date").toLocalDate());
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

	public Person getPerson(String lastname, String firstPerson) {
		try(Connection connection = DataSourceFactory.getDataSource().getConnection();) {
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE name = ?")) {
				statement.setString(1, name);
				try(ResultSet results = statement.executeQuery()) {
					if(results.next()) {
						return new Person(results.getInt("idgenre"), results.getString("name"));
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

	public void addPerson(String name) {
//		try(Connection connection = DataSourceFactory.getDataSource().getConnection();) {
//			String sqlQuery = "INSERT INTO genre(name) VALUES(?)";
//			try(PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
//				statement.setString(1, name);
//				statement.executeUpdate();
//			}
//		} catch(SQLException e) {
//			// Manage Exception
//	        e.printStackTrace();
//		}
	}
}
