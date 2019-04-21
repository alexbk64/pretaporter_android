package com.example.alexandrebornerand.pretaporter.Model;
import java.util.List;
import java.util.Vector;

public class Staff extends User {
	private String _employee_number;
	public Customer _view;
	public Vector<Enquiry> _unnamed_Enquiry_ = new Vector<Enquiry>();
	public HelpDesk _unnamed_HelpDesk_;

	public Staff(String fist_name, String surname, String email_address, String dob, String id) {
		super(fist_name, surname, email_address, dob, id);
	}

	public List<Enquiry> viewOpenEnquiries() {
		throw new UnsupportedOperationException();
	}
}