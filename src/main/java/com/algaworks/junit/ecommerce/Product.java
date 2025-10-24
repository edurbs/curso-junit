package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

	private Long id;
	private String name;
	private String description;
	private BigDecimal value;

	public Product(Long id, String name, String description, BigDecimal value) {
		Objects.requireNonNull(id);
		Objects.requireNonNull(name);
		Objects.requireNonNull(description);
		Objects.requireNonNull(value);
		validateValue(value);
		this.id = id;
		this.name = name;
		this.description = description;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getValue() {
		return value;
	}

	private void validateValue(BigDecimal value) {
		if (value.compareTo(BigDecimal.ONE) < 0) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(value, product.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, description, value);
	}
}