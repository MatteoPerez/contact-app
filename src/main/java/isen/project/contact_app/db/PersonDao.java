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
    public PersonDao() {
        try(Connection connection = DataSourceFactory.getDataSource().getConnection()) {
            try(Statement stmt = connection.createStatement();) {
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
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> listPersons() {
        List<Person> listOfPersons = new ArrayList<>();

        try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet results = statement.executeQuery("SELECT * FROM person")) {
                    while (results.next()) {
                        // Récupérer la date de naissance avec une gestion améliorée
                        String birthDateString = results.getString("birth_date");
                        Date birthDate = null;
                        
                        if (birthDateString != null && !birthDateString.isEmpty()) {
                            try {
                                birthDate = Date.valueOf(birthDateString);  // Tentative de conversion
                            } catch (IllegalArgumentException e) {
                                System.out.println("Erreur de format de date: " + birthDateString);
                            }
                        }

                        Person person = new Person(
                                results.getInt("idperson"),
                                results.getString("lastname"),
                                results.getString("firstname"),
                                results.getString("nickname"),
                                results.getString("phone_number"),
                                results.getString("address"),
                                results.getString("email_address"),
                                birthDate  // Passer la date qui peut être null
                        );
                        listOfPersons.add(person);
                    }
                    return listOfPersons;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Person getPersonById(Integer id) {
        try(Connection connection = DataSourceFactory.getDataSource().getConnection();) {
            try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE idperson = ?")) {
                statement.setInt(1, id);
                try(ResultSet results = statement.executeQuery()) {
                    if(results.next()) {
                        String birthDateString = results.getString("birth_date");
                        Date birthDate = null;

                        if (birthDateString != null && !birthDateString.isEmpty()) {
                            try {
                                birthDate = Date.valueOf(birthDateString);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Erreur de format de date: " + birthDateString);
                            }
                        }

                        return new Person(
                                results.getInt("idperson"), 
                                results.getString("lastname"),
                                results.getString("firstname"),
                                results.getString("nickname"),
                                results.getString("phone_number"),
                                results.getString("address"),
                                results.getString("email_address"),
                                birthDate
                        );
                    } else {
                        return null;
                    }
                }
            }
        } catch(SQLException e) {
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
            e.printStackTrace();
            return null;
        }
    }

    public void updatePerson(Person person) {
        try(Connection connection = DataSourceFactory.getDataSource().getConnection();) {
            try(PreparedStatement statement = connection.prepareStatement("UPDATE person SET lastname = ?, firstname = ?, nickname = ?, phone_number = ?, address = ?, email_address = ?, birth_date = ? WHERE idperson = ?")) {
                statement.setString(1, person.getLastName());
                statement.setString(2, person.getFirstName());
                statement.setString(3, person.getNickName());
                statement.setString(4, person.getPhoneNumber());
                statement.setString(5, person.getAddress());
                statement.setString(6, person.getEmailAddress());
                statement.setDate(7, person.getBirthDate());
                statement.setInt(8, person.getId());
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
