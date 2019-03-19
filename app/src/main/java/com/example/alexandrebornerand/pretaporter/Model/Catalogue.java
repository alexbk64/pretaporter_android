package com.example.alexandrebornerand.pretaporter.Model;
import java.util.List;

public class Catalogue {
	private List<Product> _weddings;
	private List<Product> _grad_ceremonies;
	private List<Product> _prom;
	private List<Product> _office_party;
	private List<Product> _date_night;
	private List<Product> _black_tie;
	public Product _holds;

	public void addProduct(Object aProduct_product) {
		throw new UnsupportedOperationException();
	}

	public void removeProduct(String aProduct_id) {
		throw new UnsupportedOperationException();
	}

	public List<Product> findProducts(String aKeyword) {
		throw new UnsupportedOperationException();
	}
}