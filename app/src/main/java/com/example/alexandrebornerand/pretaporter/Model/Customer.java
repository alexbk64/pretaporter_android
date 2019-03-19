package com.example.alexandrebornerand.pretaporter.Model;

import java.util.List;
import java.util.Vector;

public class Customer extends User {
	private String _billing_address;
	private String _contact_number;
	private boolean _active;
	private int _rating;
	private float _responsiveness;
	private String _username;
	private String _password;
	private String _email;
	private List<Message> _messages;
	private List<Enquiry> _enquiries;
	public Rental _involved_in;
	public Vector<Transaction> _history = new Vector<Transaction>();
	public MessageBox _unnamed_MessageBox_;
	public Bag _unnamed_Bag_;
	public HelpDesk _unnamed_HelpDesk_;

	public String getBilling_address() {
		return this._billing_address;
	}

	public void setBilling_address(String aBilling_address) {
		this._billing_address = aBilling_address;
	}

	public String getContactNumber() {
		throw new UnsupportedOperationException();
	}

	public void setContactNumber(String aContact_number) {
		throw new UnsupportedOperationException();
	}

	public boolean getActive() {
		return this._active;
	}

	public int getRating() {
		return this._rating;
	}

	public float getResponsiveness() {
		return this._responsiveness;
	}

	public void setEmail(String aEmail) {
		this._email = aEmail;
	}

	public Enquiry contactSupport() {
		throw new UnsupportedOperationException();
	}
}