package isen.project.contact_app.db;

import java.time.LocalDate;

public class Person {
	private Integer id;
	private String lastName;
	private String firstName;
	private String nickName;
	private String phoneNumber;
	private String adress;
	private String emailAdress;
	private LocalDate birthDate;
	
	public Person(Integer id, String lastName, String firstName, String nickName, String phoneNumber, String adress, String emailAdress, LocalDate birthDate) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.adress = adress;
		this.emailAdress = adress;
		this.birthDate = birthDate;
	}
}
