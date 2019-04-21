package com.example.alexandrebornerand.pretaporter.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Rental {
	private String _lister;
	private String _renter;
	private float _amount;
	private String _rental_id;
	private String _fromDate;
	private String _toDate;
	private Product _product;
	private ArrayList<Date> daysRented;
	private Boolean active;

	public Rental() {}

	public Rental(String _lister, String _renter, float _amount, String _rental_id, String _fromDate, String _toDate, Product _product) {
		this._lister = _lister;
		this._renter = _renter;
		this._amount = _amount;
		this._rental_id = _rental_id;
		this._fromDate = _fromDate;
		this._toDate = _toDate;
		this._product = _product;
//		daysRented = new ArrayList<>();


	}

	public Boolean isActive() {
		for (Date day : getDaysRented()) {
			if (sameDay(day, new Date())){
//				this.active = true;
				return true;
			}
		}
//		this.active = false;
		return false;
	}

//	public void initialiseActiveStatus() {
//		for (Date day : getDaysRented()) {
//			if (sameDay(day, new Date())){
//				this.active = true;
//				return;
//			}
//			this.active = false;
//		}
//	}
	public void setActive(Boolean active) {
		this.active = active;
	}

	public String get_lister() {
		return _lister;
	}

	public void set_lister(String _lister) {
		this._lister = _lister;
	}

	public String get_renter() {
		return _renter;
	}

	public void set_renter(String _renter) {
		this._renter = _renter;
	}

	public float get_amount() {
		return _amount;
	}

	public void set_amount(float _amount) {
		this._amount = _amount;
	}

	public String get_rental_id() {
		return _rental_id;
	}

	public void set_rental_id(String _rental_id) {
		this._rental_id = _rental_id;
	}

	public String get_fromDate() {
		return _fromDate;
	}

	public void set_fromDate(String _fromDate) {
		this._fromDate = _fromDate;
	}

	public String get_toDate() {
		return _toDate;
	}

	public void set_toDate(String _toDate) {
		this._toDate = _toDate;
	}

	public Product get_product() {
		return _product;
	}

	public void set_product(Product _product) {
		this._product = _product;
	}

	public void setDaysRented(ArrayList<Date> daysRented) {
		this.daysRented = daysRented;
	}

	public ArrayList<Date> getDaysRented() {
		return daysRented;
	}



	private boolean sameDay(Date d1, Date d2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(d1);
		cal2.setTime(d2);
		return (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
	}


	//	private List<String> _conditions;
//	private Date _date;
//	private PickUp _exchange_method;
//	private String _exchange_location;
//	private Return _return_method;
//	private String _return_location;
	//private Payment _payment_details;
//	private RentalStatus _status;
//	public Vector<Customer> _involved_in = new Vector<Customer>();
//	public Transaction _unnamed_Transaction_;
//	public Product _unnamed_Product_;
//	public RentalStatus _unnamed_RentalStatus_;
//	public PickUp _unnamed_PickUp_;
//	public Return _unnamed_Return_;

//	public float getAmount() {
//		return this._amount;
//	}
//
//	public int getProductID() {
//		throw new UnsupportedOperationException();
//	}
//
//	public void initiateRental() {
//		throw new UnsupportedOperationException();
//	}
//
//	public void requestFitting() {
//		throw new UnsupportedOperationException();
//	}
//
//	public String getLister() {
//		throw new UnsupportedOperationException();
//	}
//
//	public String getRenter() {
//		throw new UnsupportedOperationException();
//	}
//
//	public String getConditions() {
//		throw new UnsupportedOperationException();
//	}
//
//	public Date getDate() {
//		return this._date;
//	}
//
//	public int getRentalNumber() {
//		throw new UnsupportedOperationException();
//	}
//
//	public Date getFromDate() {
//		return this._fromDate;
//	}
//
//	public Date getToDate() {
//		return this._toDate;
//	}
//
//	public String getExchange_method() {
//		throw new UnsupportedOperationException();
//	}
//
//	public String getExchange_location() {
//		return this._exchange_location;
//	}
//
//	public String getReturn_method() {
//		throw new UnsupportedOperationException();
//	}
//
//	public String getReturn_location() {
//		return this._return_location;
//	}
//
//	/*public Payment getPayment_details() {
//		return this._payment_details;
//	}*/
//
//	public void setConditions(List<String> aConditions) {
//		throw new UnsupportedOperationException();
//	}
}