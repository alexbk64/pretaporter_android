package com.example.alexandrebornerand.pretaporter.Model;
import com.example.alexandrebornerand.pretaporter.Model.Customer;
import com.example.alexandrebornerand.pretaporter.Model.Enquiry;

import java.util.List;
import java.util.Vector;

public class HelpDesk {
	private List<Enquiry> _open_enquiries;
	private List<Enquiry> _closed_enquiries;
	public Customer _unnamed_Customer_;
	public Staff _unnamed_Staff_;
	public Vector<Enquiry> _unnamed_Enquiry_ = new Vector<Enquiry>();

	public void addEnquiry(Object aEnquiry_Enquiry) {
		throw new UnsupportedOperationException();
	}

	public List<Enquiry> getOpenEnquiries() {
		throw new UnsupportedOperationException();
	}

	public Enquiry findEnquiryByID(String aId) {
		throw new UnsupportedOperationException();
	}

	public List<Enquiry> findEnquiriesByUser(String aAuthor) {
		throw new UnsupportedOperationException();
	}
}