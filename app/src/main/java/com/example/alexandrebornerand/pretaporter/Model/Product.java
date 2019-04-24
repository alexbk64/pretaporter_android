package com.example.alexandrebornerand.pretaporter.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Product implements Serializable {
	private String id;
	private String _name;
	private String _description;
	private float _daily_fee;
	private double rating;
	private ArrayList<String> images;
	private User lister;
	private boolean _available;
	private ArrayList<String> datesUnavailable;
	private int minimumNumberOfDays;
	final private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private String queryName;
	private String queryDescription;
	private String category;
	private String queryCategory;
	private String colour;
	private String queryColour;
	private String size;
	private String querySize;

	//empty constructor needed for firebase
	public Product(){}

	//int
	public Product(String _id, String _name, String _description, float _daily_fee, double _rating, ArrayList<String> _images, User _lister, String category, String size, String colour) {
		this.id = _id;
		this._name = _name;
		this._description = _description;
		this._daily_fee = _daily_fee;
		this.rating = _rating;
		this.images = _images;
		this.lister = _lister;
		this.datesUnavailable=new ArrayList<>();
		this.minimumNumberOfDays = 0;
		this._available=true;

		//TODO: allow user to set category. uncomment line below if stops working
		this.category = category;
		this.size = size;
		this.colour = colour;
		this.queryName=_name.toLowerCase();
		this.queryDescription=_description.toLowerCase();
		this.queryCategory = category.toLowerCase();
		this.queryColour = colour.toLowerCase();
		this.querySize = size.toLowerCase();

	}


	public boolean is_available() {
		return _available;
	}

	public void set_available(boolean _available) {

		this._available = _available;
	}

	public boolean checkAvailability(Date aFrom, Date aTo) {
		throw new UnsupportedOperationException();
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}

	//needed for firebase querying
	public String getQueryName() {
		return queryName;
	}
	public String getQueryDescription() {
		return queryDescription;
	}
	public String getQueryCategory() {
		return queryCategory;
	}
	public String getQueryColour() {
		return queryColour;
	}
	public String getQuerySize() {
		return querySize;
	}

	public void set_lister(User _lister) {
		this.lister = _lister;
	}

	public String getId() {
		return this.id;
	}

	public void set_id(String _id) {
		this.id = _id;
	}

	public String getName() {
		return this._name;
	}

	public void setName(String aName) {
		this._name = aName;
	}

	public String getDescription() {
		return this._description;
	}

	public void setDescription(String aDescription) {
		this._description = aDescription;
	}

	public float getDaily_fee() {
		return this._daily_fee;
	}

	public void setDaily_fee(float aDaily_fee) {
		this._daily_fee = aDaily_fee;
	}

	public ArrayList<String> getDatesUnavailable() {
		return datesUnavailable;
	}

	public void setDatesUnavailable(ArrayList<String> datesUnavailable) {
		this.datesUnavailable = datesUnavailable;
	}

	public void updateDatesUnavailable(ArrayList<String> datesUnavailable) {
		//if no previously set unavailable dates, return newly set datesUnavailable
		if (this.datesUnavailable==null)
			this.datesUnavailable=datesUnavailable;
		//otherwise add these dates to existing list of dates, removing any duplicates
		else {
			//2 step process: remove all elements of original list from new list,
			//then add all remaining elements from new list to original list. original list is now updated.
			//gives combined list without duplicates
			ArrayList<String> temp = new ArrayList<>(datesUnavailable);
			temp.removeAll(this.datesUnavailable);
			this.datesUnavailable.addAll(temp);
		}
	}


	public double getRating() {
		return this.rating;
	}

//	public String getImage() {
	public ArrayList<String> getImages() {
		return images;
	}
//	public void setImage(String image) {
	public void setImages(ArrayList<String> images) {
		this.images = images;
	}


	public User getLister() {
		return this.lister;
	}

	public int getMinimumNumberOfDays() {
		return minimumNumberOfDays;
	}

	public void setMinimumNumberOfDays(int minimumNumberOfDays) {
		this.minimumNumberOfDays = minimumNumberOfDays;
	}

}