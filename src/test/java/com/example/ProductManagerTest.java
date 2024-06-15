package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductManagerTest {
    private ProductManager productManager;

    @BeforeEach
    public void setUp() {
        productManager = new ProductManager();
    }

    @Test
    public void testAddProduct() {
        Product product = new Product("Laptop", 1000.0);
        productManager.addProduct(product);

        assertNotNull(productManager.getProduct("Laptop"));
    }

    @Test
    public void testGetProduct() {
        Product product = new Product("Smartphone", 700.0);
        productManager.addProduct(product);

        Product retrievedProduct = productManager.getProduct("Smartphone");
        assertEquals("Smartphone", retrievedProduct.getName());
        assertEquals(700.0, retrievedProduct.getPrice());
    }

    @Test
    public void testGetProductsAbovePrice() {
        productManager.addProduct(new Product("Laptop", 1000.0));
        productManager.addProduct(new Product("Smartphone", 700.0));
        productManager.addProduct(new Product("Tablet", 300.0));

        List<Product> expensiveProducts = productManager.getProductsAbovePrice(600.0);
        assertEquals(2, expensiveProducts.size());
        assertTrue(expensiveProducts.stream().anyMatch(p -> p.getName().equals("Laptop")));
        assertTrue(expensiveProducts.stream().anyMatch(p -> p.getName().equals("Smartphone")));
    }

    @Test
    public void testGetTotalPrice() {
        productManager.addProduct(new Product("Laptop", 1000.0));
        productManager.addProduct(new Product("Smartphone", 700.0));
        productManager.addProduct(new Product("Tablet", 300.0));

        double totalPrice = productManager.getTotalPrice();
        assertEquals(2000.0, totalPrice);
    }

    @Test
    public void testGetProductNotFound() {
        Product product = productManager.getProduct("Nonexistent Product");
        assertNull(product);
    }
}
