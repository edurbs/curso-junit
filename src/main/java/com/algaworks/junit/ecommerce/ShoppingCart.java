package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingCart {

	private final Customer customer;
	private final List<ShoppingCartItem> items;

	public ShoppingCart(Customer customer) {
		this(customer, new ArrayList<>());
	}

	public ShoppingCart(Customer customer, List<ShoppingCartItem> items) {
		Objects.requireNonNull(customer);
		Objects.requireNonNull(items);
		this.customer = customer;
		this.items = new ArrayList<>(items); //Creates list in case an immutable one is passed
	}

	public List<ShoppingCartItem> getItems() {
		//TODO must return a new list so the old one is not altered
		return null;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void addProduct(Product product, int quantity) {
		//TODO parameters cannot be null, should return an exception
		//TODO quantity cannot be less than 1
		//TODO should increment quantity if product already exists
	}

	public void removeProduct(Product product) {
		//TODO parameter cannot be null, should return an exception
		//TODO if product does not exist, should return an exception
		//TODO should remove product regardless of quantity
	}

	public void increaseProductQuantity(Product product) {
		//TODO parameter cannot be null, should return an exception
		//TODO if product does not exist, should return an exception
		//TODO should increase product quantity by one
	}

    public void decreaseProductQuantity(Product product) {
		//TODO parameter cannot be null, should return an exception
		//TODO if product does not exist, should return an exception
		//TODO should decrease product quantity by one, if there is only one product, should remove from list
	}

    public BigDecimal getTotalValue() {
		//TODO implement sum of total value of all items
		return null;
    }

	public int getTotalQuantityOfProducts() {
		//TODO returns total quantity of items in cart
		//TODO Example in a cart with 2 items, with quantity 2 and 3 for each item respectively, should return 5
		return 0;
	}

	public void empty() {
		//TODO should remove all items
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ShoppingCart that = (ShoppingCart) o;
		return Objects.equals(items, that.items) && Objects.equals(customer, that.customer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(items, customer);
	}
}