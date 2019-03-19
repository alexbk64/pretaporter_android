package com.example.alexandrebornerand.pretaporter.Model;

import java.util.Date;
import java.util.Vector;


public class Transaction {
	private int _id;
	private float _amount;
	private Date _date;
	private Rental _rental;
	private int _product_id;
	private String _lister_id;
	private String _renter_id;
	public Vector<Customer> _history = new Vector<Customer>();
	public Rental _unnamed_Rental_;

	public void generateInvoice() {
		throw new UnsupportedOperationException();
	}

	public int getId() {
		return this._id;
	}

	public Date getDate() {
		return this._date;
	}

	public float getAmount() {
		return this._amount;
	}

	public Rental getRental() {
		return this._rental;
	}

	public int getProduct_id() {
		return this._product_id;
	}

	public String getLister_id() {
		return this._lister_id;
	}

	public String getRenter_id() {
		return this._renter_id;
	}
}