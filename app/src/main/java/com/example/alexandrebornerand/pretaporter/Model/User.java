package com.example.alexandrebornerand.pretaporter.Model;

import java.io.Serializable;

public class User implements Serializable {
	private String first_name;
	private String surname;
	private String email_address;
	private String dob;
	private String id;
	private String fbID;
	private String photoUrl;

	public User() {}
	public User(String first_name, String surname, String email_address, String dob, String id) {
		this.first_name = first_name;
		this.surname = surname;
		this.email_address = email_address;
		this.dob = dob;
		this.id = id;
		this.fbID = "N/A";
		photoUrl="";
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getFbID() {
		return fbID;
	}

	public void setFbID(String fbID) {
		this.fbID = fbID;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail_address() {
		return email_address;
	}

	public String getDob() {
		return dob;
	}

	public String getId() { return id;}

	public String getDisplayName() {
		return getFirst_name()+" "+getSurname();
	}

}