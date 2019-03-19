package com.example.alexandrebornerand.pretaporter.Model;

import java.util.Date;
import java.util.List;

public class Product {
	private int _id;
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
//	private boolean _available;
//	//should make rating a double
	private double _rating;
	private int image;
//	private String _condition;
//	private String[] _faults;
//	private Customer _lister;
//	private String _location;
//	private PickUp _receiptMethod;
//	private Return _returnMethod;
//	public Catalogue _holds;
//	public Rental _unnamed_Rental_;
//	public Vector<Category> _unnamed_Category_ = new Vector<Category>();
//	public PickUp _unnamed_PickUp_;
//	public Return _unnamed_Return_;
//	public ProductStatus _unnamed_ProductStatus_;

	public Product(int _id/*,ImageView _image_main*/, String _name, String _description, float _daily_fee,/* float _cleaning_fee, float _retail_rate, String _colour, List<Category> _category, String _sex, String _size, boolean _available,*/ double _rating, int _image/*, String _condition, String[] _faults, Customer _lister, String _location, PickUp _receiptMethod, Return _returnMethod, Catalogue _holds, Rental _unnamed_Rental_, Vector<Category> _unnamed_Category_, PickUp _unnamed_PickUp_, Return _unnamed_Return_, ProductStatus _unnamed_ProductStatus_*/) {
		this._id = _id;
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
		this._rating = _rating;
		this.image = _image;
//		this._condition = _condition;
//		this._faults = _faults;
//		this._lister = _lister;
//		this._location = _location;
//		this._receiptMethod = _receiptMethod;
//		this._returnMethod = _returnMethod;
//		this._holds = _holds;
//		this._unnamed_Rental_ = _unnamed_Rental_;
//		this._unnamed_Category_ = _unnamed_Category_;
//		this._unnamed_PickUp_ = _unnamed_PickUp_;
//		this._unnamed_Return_ = _unnamed_Return_;
//		this._unnamed_ProductStatus_ = _unnamed_ProductStatus_;
	}

	public boolean checkAvailability(Date aFrom, Date aTo) {
		throw new UnsupportedOperationException();
	}

//	public ImageView get_image_main() {
//		return _image_main;
//	}
//
//	public void set_image_main(ImageView _image_main) {
//		this._image_main = _image_main;
//	}

	public int getId() {
		return this._id;
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
//
//	public boolean getAvailable() {
//		return this._available;
//	}
//
//	public void setAvailable(boolean aAvailable) {
//		this._available = aAvailable;
//	}

	public double getRating() {
		return this._rating;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
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

//	public String getLister() {
//		throw new UnsupportedOperationException();
//	}

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

	public Product getProduct(String aProduct_id) {
		throw new UnsupportedOperationException();
	}

	public List<Date> getAvailability() {
		throw new UnsupportedOperationException();
	}

	public Rental initiateRental(String aProduct_id, Customer aLister, Date aFrom, Date aTo, float aTotal, PickUp aReceipt, Return aReturn_method, RentalStatus aStatus) {
		throw new UnsupportedOperationException();
	}
}