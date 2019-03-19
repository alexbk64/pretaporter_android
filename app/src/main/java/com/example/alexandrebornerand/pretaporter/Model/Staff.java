package com.example.alexandrebornerand.pretaporter.Model;
import com.example.alexandrebornerand.pretaporter.Model.Customer;
import com.example.alexandrebornerand.pretaporter.Model.Enquiry;
import com.example.alexandrebornerand.pretaporter.Model.HelpDesk;

import java.util.List;
import java.util.Vector;

public class Staff extends User {
	private String _employee_number;
	public Customer _view;
	public Vector<Enquiry> _unnamed_Enquiry_ = new Vector<Enquiry>();
	public HelpDesk _unnamed_HelpDesk_;

	public List<Enquiry> viewOpenEnquiries() {
		throw new UnsupportedOperationException();
	}
}