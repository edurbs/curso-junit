package com.algaworks.junit.ecommerce;

import com.algaworks.junit.blog.exception.BusinessRuleException;
import com.algaworks.junit.custom.HumanPhraseDisplayNameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(HumanPhraseDisplayNameGenerator.class)
class ShoppingCartTest {

    ShoppingCart sut;
    List<ShoppingCartItem> shoppingCartItems;

    ShoppingCart create() {
        Customer customer = new Customer(1L, "Customer Name");
        return new ShoppingCart(customer, getItems());
    }

    Product getProduct() {
        return new Product(1L, "Product 1", "Description", BigDecimal.TEN);
    }

    List<ShoppingCartItem> getItems() {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(getProduct(), 1);
        shoppingCartItems = new ArrayList<>();
        shoppingCartItems.add(shoppingCartItem);
        return shoppingCartItems;
    }

    @BeforeEach
    void prepare() {
        sut = create();
    }

    @Nested
    class GivenList {

        @Test
        void whenGetItems_thenReturnNewList() {
            var newList = sut.getItems();
            assertNotSame(shoppingCartItems, newList);
        }

        @Test
        void whenGetTotalValue_thenReturnSumValueOfAllItems() {
            BigDecimal totalExpected = BigDecimal.valueOf(100);
            sut.addProduct(getProduct(), 9);
            BigDecimal total = sut.getTotalValue();
            assertEquals(totalExpected, total);

        }

        @Test
        void whenGetTotalQuantityOfProducts_thenReturnSumOfEachQuantityOfItemsInCart() {
            int quantityExpected = 10;
            sut.addProduct(getProduct(), 9);
            int quantityActual = sut.getTotalQuantityOfProducts();
            assertEquals(quantityExpected, quantityActual);

        }

        @Test
        void whenEmpty_thenRemoveAllItems() {
            Executable empty = () -> sut.empty();
            assertDoesNotThrow(empty);
            int listSize = sut.getItems().size();
            assertEquals(0, listSize);
        }

        @Nested
        class WhenAddProduct {

            @Test
            void andIsProductNull_thenThrowException() {
                Executable addProduct = () -> sut.addProduct(null, 1);
                assertThrows(Exception.class, addProduct);
            }

            @Test
            void andQuantityIsLessThanOne_thenThrowException() {
                Executable addProduct = () -> sut.addProduct(getProduct(), 0);
                BusinessRuleException exception = assertThrows(BusinessRuleException.class, addProduct);
                assertEquals("Quantity can't be less than 1.", exception.getMessage());
            }

            @Test
            void andProductWasAlreadyAdded_thenIncrementQuantity() {
                var product1 = getProduct();
                sut.addProduct(product1, 1);
                var quantityFound = sut.getItems().stream()
                        .filter(shoppingCartItem -> shoppingCartItem.getProduct().equals(product1))
                        .mapToInt(ShoppingCartItem::getQuantity)
                        .sum();
                assertEquals(2, quantityFound);
            }
        }

        @Nested
        class WhenRemoveProduct {

            @Test
            void andProductIsNull_thenThrowException() {
                Executable remove = () -> sut.removeProduct(null);
                assertThrows(NullPointerException.class, remove);
            }

            @Test
            void andListNotContainsProduct_thenThrowException() {
                Product productNotInList = new Product(9L, "Name", "Descrition", BigDecimal.TWO);
                Executable remove = () -> sut.removeProduct(productNotInList);
                BusinessRuleException exception = assertThrows(BusinessRuleException.class, remove);
                assertEquals("Product does not exists.", exception.getMessage());
            }

            @Test
            void andListContainsProduct_thenRemoveProduct() {
                Product productToRemove = getProduct();
                Executable remove = () -> sut.removeProduct(productToRemove);
                assertDoesNotThrow(remove);
                List<ShoppingCartItem> items = sut.getItems();
                boolean removed = items.stream()
                        .noneMatch(item -> item.getProduct().equals(productToRemove));
                assertTrue(removed);

            }
        }

        @Nested
        class WhenIncreaseOneProductQuantity {

            @Test
            void andProductIsNull_thenThrowException() {
                Executable increaseOne = () -> sut.increaseOneProductQuantity(null);
                assertThrows(NullPointerException.class, increaseOne);
            }

            @Test
            void andListNotContains_thenThrowException() {
                Product newProduct = new Product(9L, "Name", "Description", BigDecimal.TEN);
                Executable increaseOne = () -> sut.increaseOneProductQuantity(newProduct);
                BusinessRuleException exception = assertThrows(BusinessRuleException.class, increaseOne);
                assertEquals("Product does not exists in shopping cart.", exception.getMessage());
            }

            @Test
            void andListContains_thenIncreateQuantityByOne() {
                Product product = getProduct();
                Executable increaseOne = () -> sut.increaseOneProductQuantity(product);
                assertDoesNotThrow(increaseOne);
                int newQuantity = sut.getItems().stream().filter(item -> item.getProduct().equals(product))
                        .mapToInt(ShoppingCartItem::getQuantity)
                        .sum();
                assertEquals(2, newQuantity);
            }
        }

        @Nested
        class WhenDecreaseOneProductQuantity {

            @Test
            void andProductIsNull_thenThrowException() {
                Executable decrease = () -> sut.decreaseOneProductQuantity(null);
                assertThrows(NullPointerException.class, decrease);
            }

            @Test
            void andListNotContains_thenThrowBusinessException() {
                Product newProduct = new Product(9L, "Name", "Description", BigDecimal.TEN);
                Executable decrease = () -> sut.decreaseOneProductQuantity(newProduct);
                BusinessRuleException exception = assertThrows(BusinessRuleException.class, decrease);
                assertEquals("The shopping cart does not have the product", exception.getMessage());
            }

            @Nested
            class AndListContains {

                @Test
                void andQuantityIsBiggerThenOne_thenDecreaseByOne() {
                    Product newProduct = new Product(9L, "Name", "Description", BigDecimal.TEN);
                    sut.addProduct(newProduct, 10);
                    Executable decrease = () -> sut.decreaseOneProductQuantity(newProduct);
                    assertDoesNotThrow(decrease);
                    Integer quantityActual = sut.getItems().stream()
                            .filter(items -> items.getProduct().equals(newProduct))
                            .findFirst()
                            .map(ShoppingCartItem::getQuantity)
                            .orElse(null);
                    assertEquals(9, quantityActual);
                }

                @Test
                void andQuantityIsOne_thenRemoveFromList() {
                    Executable decrease = () -> sut.decreaseOneProductQuantity(getProduct());
                    assertDoesNotThrow(decrease);
                    boolean listContains = sut.getItems().stream()
                            .anyMatch(item -> item.getProduct().equals(getProduct()));
                    assertFalse(listContains);
                }
            }

        }

    }

}