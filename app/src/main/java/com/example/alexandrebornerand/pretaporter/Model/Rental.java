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

	}

	public Boolean isActive() {
		for (Date day : getDaysRented()) {
			if (sameDay(day, new Date())){
				return true;
			}
		}
		return false;
	}

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

}