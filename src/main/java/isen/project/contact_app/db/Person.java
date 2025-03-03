package isen.project.contact_app.db;

import java.sql.Date;

public class Person {
	private Integer id;
	private String lastName;
	private String firstName;
	private String nickName;
	private String phoneNumber;
	private String address;
	private String emailAddress;
	private Date birthDate;
	
	public Person(Integer id, String lastName, String firstName, String nickName, String phoneNumber, String address, String emailAddress, Date birthDate) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.emailAddress = emailAddress;
		this.birthDate = birthDate;
	}
	
	public Person(String lastName, String firstName, String nickName, String phoneNumber, String address, String emailAddress, Date birthDate) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.emailAddress = emailAddress;
		this.birthDate = birthDate;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String adress) {
		this.address = adress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAdress) {
		this.emailAddress = emailAdress;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
