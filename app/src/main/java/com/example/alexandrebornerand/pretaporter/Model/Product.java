package com.example.alexandrebornerand.pretaporter.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Product implements Serializable {
	private String id;
////	private ImageView _image_main;
	private String _name;
	private String _description;
	private float _daily_fee;
//	private float _cleaning_fee;
//	private float _retail_rate;
//	private String _colour;
//	private List<Category> _category;
//	private String _sex;
//	private String _size;

//	//should make rating a double
	private double rating;
	private ArrayList<String> images;
//	private String image;
//	private String _condition;
//	private String[] _faults;
	private User lister;
//	private String _location;
//	private PickUp _receiptMethod;
//	private Return _returnMethod;
//	public Catalogue _holds;
//	public Rental _unnamed_Rental_;
//	public Vector<Category> _unnamed_Category_ = new Vector<Category>();
//	public PickUp _unnamed_PickUp_;
//	public Return _unnamed_Return_;
//	public ProductStatus _unnamed_ProductStatus_;
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

	//empty constructor
	public Product(){}

	//int
	public Product(String _id, String _name, String _description, float _daily_fee, double _rating, ArrayList<String> _images, User _lister, String category, String size, String colour) {
		this.id = _id;
//		this._image_main = _image_main;
		this._name = _name;
		this._description = _description;
		this._daily_fee = _daily_fee;
//		this._cleaning_fee = _cleaning_fee;
//		this._retail_rate = _retail_rate;
//		this._colour = _colour;
//		this._category = _category;
//		this._sex = _sex;
//		this._size = _size;
//		this._available = _available;
		this.rating = _rating;
		this.images = _images;
//		this._condition = _condition;
//		this._faults = _faults;
		this.lister = _lister;
//		this._location = _location;
//		this._receiptMethod = _receiptMethod;
//		this._returnMethod = _returnMethod;
//		this._holds = _holds;
//		this._unnamed_Rental_ = _unnamed_Rental_;
//		this._unnamed_Category_ = _unnamed_Category_;
//		this._unnamed_PickUp_ = _unnamed_PickUp_;
//		this._unnamed_Return_ = _unnamed_Return_;
//		this._unnamed_ProductStatus_ = _unnamed_ProductStatus_;
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


//		Date today = new Date();
//		for (String date : getDatesUnavailable()) {
//			try {
//				if (sameDay(simpleDateFormat.parse(date), today)){
//					this._available=false;break;
//				}
//			}catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
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
	//	public float getCleaning_fee() {
//		return this._cleaning_fee;
//	}
//
//	public void setCleaning_fee(float aCleaning_fee) {
//		this._cleaning_fee = aCleaning_fee;
//	}
//
//	public float getRetail_rate() {
//		return this._retail_rate;
//	}
//
//	public void setRetail_rate(float aRetail_rate) {
//		this._retail_rate = aRetail_rate;
//	}
//
//	public String getColour() {
//		return this._colour;
//	}
//
//	public void setColour(String aColour) {
//		this._colour = aColour;
//	}
//
//	public List<Category> getCategory() {
//		return this._category;
//	}
//	public void setCategory(List<Category> aCategory) {
//		throw new UnsupportedOperationException();
//	}
//
//	public boolean getSex() {
//		throw new UnsupportedOperationException();
//	}
//
//	public void setSex(boolean aSex) {
//		throw new UnsupportedOperationException();
//	}
//	public String getSize() {
//		return this._size;
//	}
//
//	public void setSize(String aSize) {
//		this._size = aSize;
//	}

	public void updateDatesUnavailable(ArrayList<String> datesUnavailable) {
		if (this.datesUnavailable==null)
			this.datesUnavailable=datesUnavailable;
		else {
//		for (String date : datesUnavailable) {
//			this.datesUnavailable.add(date);
//		}
			//2 step process: remove all elements of first list from second list,
			//then add first list to second list
			//gives combined list without duplicates
			ArrayList<String> temp = new ArrayList<>(datesUnavailable);
			temp.removeAll(this.datesUnavailable);
			this.datesUnavailable.addAll(temp);
		}
	}


//	public boolean getAvailable() {
//		return this._available;
//	}
//
//	public void setAvailable(boolean aAvailable) {
//		this._available = aAvailable;
//	}

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

	//	public String getCondition() {
//		return this._condition;
//	}
//
//	public void setCondition(String aCondition) {
//		this._condition = aCondition;
//	}
//
//	public String[] getFaults() {
//		return this._faults;
//	}
//
//	public void setFaults(String[] aFaults) {
//		this._faults = aFaults;
//	}

	public User getLister() {
		return this.lister;
	}

	public int getMinimumNumberOfDays() {
		return minimumNumberOfDays;
	}

	public void setMinimumNumberOfDays(int minimumNumberOfDays) {
		this.minimumNumberOfDays = minimumNumberOfDays;
	}


//	public String getLocation() {
//		return this._location;
//	}
//
//	public void setLocation(String aLocation) {
//		this._location = aLocation;
//	}
//
//	public PickUp getReceiptMethod() {
//		return this._receiptMethod;
//	}
//
//	public void setReceiptMethod(PickUp aReceiptMethod) {
//		this._receiptMethod = aReceiptMethod;
//	}
//
//	public Return getReturnMethod() {
//		return this._returnMethod;
//	}
//
//	public void setReturnMethod(Return aReturnMethod) {
//		this._returnMethod = aReturnMethod;
//	}

//	public Product getProduct(String aProduct_id) {
//		throw new UnsupportedOperationException();
//	}
//
//	public List<Date> getAvailability() {
//		throw new UnsupportedOperationException();
//	}
//
//	public Rental initiateRental(String aProduct_id, Customer aLister, Date aFrom, Date aTo, float aTotal, PickUp aReceipt, Return aReturn_method, RentalStatus aStatus) {
//		throw new UnsupportedOperationException();
//	}

//	private boolean sameDay(Date d1, Date d2) {
//		Calendar cal1 = Calendar.getInstance();
//		Calendar cal2 = Calendar.getInstance();
//		cal1.setTime(d1);
//		cal2.setTime(d2);
//		return (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
//	}
}