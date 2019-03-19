package com.example.alexandrebornerand.pretaporter.Model;

import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Rental {
	private Customer _lister;
	private Customer _renter;
	private List<String> _conditions;
	private Date _date;
	private int _product_id;
	private float _amount;
	private int _rental_number;
	private Date _fromDate;
	private Date _toDate;
	private PickUp _exchange_method;
	private String _exchange_location;
	private Return _return_method;
	private String _return_location;
	//private Payment _payment_details;
	private RentalStatus _status;
	public Vector<Customer> _involved_in = new Vector<Customer>();
	public Transaction _unnamed_Transaction_;
	public Product _unnamed_Product_;
	public RentalStatus _unnamed_RentalStatus_;
	public PickUp _unnamed_PickUp_;
	public Return _unnamed_Return_;

	public float getAmount() {
		return this._amount;
	}

	public int getProductID() {
		throw new UnsupportedOperationException();
	}

	public void initiateRental() {
		throw new UnsupportedOperationException();
	}

	public void requestFitting() {
		throw new UnsupportedOperationException();
	}

	public String getLister() {
		throw new UnsupportedOperationException();
	}

	public String getRenter() {
		throw new UnsupportedOperationException();
	}

	public String getConditions() {
		throw new UnsupportedOperationException();
	}

	public Date getDate() {
		return this._date;
	}

	public int getRentalNumber() {
		throw new UnsupportedOperationException();
	}

	public Date getFromDate() {
		return this._fromDate;
	}

	public Date getToDate() {
		return this._toDate;
	}

	public String getExchange_method() {
		throw new UnsupportedOperationException();
	}

	public String getExchange_location() {
		return this._exchange_location;
	}

	public String getReturn_method() {
		throw new UnsupportedOperationException();
	}

	public String getReturn_location() {
		return this._return_location;
	}

	/*public Payment getPayment_details() {
		return this._payment_details;
	}*/

	public void setConditions(List<String> aConditions) {
		throw new UnsupportedOperationException();
	}
}