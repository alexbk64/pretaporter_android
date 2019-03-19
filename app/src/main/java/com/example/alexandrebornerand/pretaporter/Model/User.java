package com.example.alexandrebornerand.pretaporter.Model;
import java.util.List;

public class User {
	private String _name;

	private String _address;
	private List<Transaction> _transactionHistory;
	private String _username;
	private String _password;
	private UserStatus _status;
	private UserType _user_type;


	public boolean logIn(String aUsername, String aPassword) {
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return this._name;
	}

	public void setName(String aName) {
		this._name = aName;
	}

	public String getAddress() {
		return this._address;
	}

	public void setAddress(String aAddress) {
		this._address = aAddress;
	}

	public void setStatus(EnquiryStatus aStatus) {
		throw new UnsupportedOperationException();
	}

	public void sendMessage(Message aMessage) {
		throw new UnsupportedOperationException();
	}

	public User searchUser(String aUsername) {
		throw new UnsupportedOperationException();
	}

	public List<Product> searchProducts(String aKeyword) {
		throw new UnsupportedOperationException();
	}

	public void viewProduct(String aProduct_id) {
		throw new UnsupportedOperationException();
	}
}