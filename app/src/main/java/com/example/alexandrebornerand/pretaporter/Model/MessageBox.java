package com.example.alexandrebornerand.pretaporter.Model;

import java.util.Date;
import java.util.List;
public class MessageBox {
	private List<Message> _inbox;
	private List<Message> _sent;
	private List<Message> _drafts;
	public Customer _unnamed_Customer_;

	public void newMessage(int aId, User aFrom, User aTo, Date aDate, String aContent) {
		throw new UnsupportedOperationException();
	}

	public List<Message> searchMessages(String aKeyword) {
		throw new UnsupportedOperationException();
	}
}