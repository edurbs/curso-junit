package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.util.Objects;

public class ShoppingCartItem {

    private final Product product;
    private int quantity;

    public ShoppingCartItem(Product product, int quantity) {
        Objects.requireNonNull(product);
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public BigDecimal getTotalValue() {
        return this.product.getValue()
                .multiply(new BigDecimal(quantity));
    }

    public void addQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
        this.quantity += quantity;
    }

    public void subtractQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if (quantity >= this.quantity) {
            throw new IllegalArgumentException();
        }
        this.quantity -= quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartItem that = (ShoppingCartItem) o;
        return quantity == that.quantity && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }
}
