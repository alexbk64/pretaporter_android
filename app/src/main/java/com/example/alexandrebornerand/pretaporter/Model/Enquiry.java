package com.example.alexandrebornerand.pretaporter.Model;

import java.util.Date;

public class Enquiry {
	private Customer _author;
	private String _subject;
	private String _body;
	private Date _date;
	private EnquiryStatus _status;
	private String _id;
	public Staff _unnamed_Staff_;
	public HelpDesk _unnamed_HelpDesk_;
	public EnquiryStatus _unnamed_EnquiryStatus_;

	public Customer getAuthor() {
		return this._author;
	}

	public String getSubject() {
		return this._subject;
	}

	public String getBody() {
		return this._body;
	}

	public Date getDate() {
		return this._date;
	}

	public EnquiryStatus getStatus() {
		return this._status;
	}

	public boolean setStatus(EnquiryStatus aStatus) {
		throw new UnsupportedOperationException();
	}
}