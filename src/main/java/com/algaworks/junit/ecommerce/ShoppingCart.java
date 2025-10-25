package com.algaworks.junit.ecommerce;

import com.algaworks.junit.blog.exception.BusinessRuleException;

import java.math.BigDecimal;
import java.util.*;

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
		this.items = new ArrayList<>(items);
	}

	public List<ShoppingCartItem> getItems() {
		return items;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void addProduct(Product product, int quantity) {
        Objects.requireNonNull(product);
        if(quantity<1){
            throw new BusinessRuleException("Quantity can't be less than 1.");
        }
        items.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse( item -> item.addQuantity(quantity),
                        () -> {
                            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(product, quantity);
                            items.add(shoppingCartItem);
                        }
                );
	}

	public void removeProduct(Product product) {
        Objects.requireNonNull(product);
        boolean removed = items.removeIf(item -> item.getProduct().equals(product));
        if(!removed){
            throw new BusinessRuleException("Product does not exists.");
        }
	}

	public void increaseOneProductQuantity(Product product) {
        Objects.requireNonNull(product);
        items.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.addQuantity(1),
                        () -> {throw new BusinessRuleException("Product does not exists in shopping cart.");}
                );

	}

    public void decreaseOneProductQuantity(Product product) {
        Objects.requireNonNull(product);
        items.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(
                        item -> {
                            int quantity = item.getQuantity();
                            if(quantity>1){
                                item.subtractQuantity(1);
                            }else{
                                removeProduct(product);
                            }
                        },
                        () -> {throw new BusinessRuleException("The shopping cart does not have the product");}
                );
	}

    public BigDecimal getTotalValue() {
		return items.stream()
                .map(ShoppingCartItem::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

	public int getTotalQuantityOfProducts() {
		return items.stream()
                .map(ShoppingCartItem::getQuantity)
                .reduce(0, Integer::sum);
	}

	public void empty() {
        items.clear();
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