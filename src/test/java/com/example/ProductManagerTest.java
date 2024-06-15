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
    public void testAddProductWithNull() {
        assertThrows(IllegalArgumentException.class, () -> productManager.addProduct(null));
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
    public void testGetProductNotFound() {
        Product product = productManager.getProduct("Nonexistent Product");
        assertNull(product);
    }

    @Test
    public void testGetProductWithNullName() {
        Product product = productManager.getProduct(null);
        assertNull(product);
    }

    @Test
    public void testGetProductWithEmptyList() {
        assertNull(productManager.getProduct("Any Product"));
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
    public void testGetProductsAbovePriceWithNullProduct() {
        productManager.addProduct(null); // Add null product to the list
        productManager.addProduct(new Product("Laptop", 1000.0));
        productManager.addProduct(new Product("Smartphone", 700.0));
        productManager.addProduct(new Product("Tablet", 300.0));

        List<Product> expensiveProducts = productManager.getProductsAbovePrice(600.0);
        assertEquals(2, expensiveProducts.size());
        assertTrue(expensiveProducts.stream().anyMatch(p -> p.getName().equals("Laptop")));
        assertTrue(expensiveProducts.stream().anyMatch(p -> p.getName().equals("Smartphone")));
    }

    @Test
    public void testGetProductsAbovePriceWithNoProducts() {
        List<Product> expensiveProducts = productManager.getProductsAbovePrice(600.0);
        assertEquals(0, expensiveProducts.size());
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
    public void testGetTotalPriceWithNullProduct() {
        productManager.addProduct(null); // Add null product to the list
        productManager.addProduct(new Product("Laptop", 1000.0));
        productManager.addProduct(new Product("Smartphone", 700.0));
        productManager.addProduct(new Product("Tablet", 300.0));

        double totalPrice = productManager.getTotalPrice();
        assertEquals(2000.0, totalPrice); // Null product should not affect the total price
    }

    @Test
    public void testGetTotalPriceWithNoProducts() {
        double totalPrice = productManager.getTotalPrice();
        assertEquals(0.0, totalPrice);
    }

    @Test
    public void testGetProductsAbovePriceWithExactPrice() {
        productManager.addProduct(new Product("Laptop", 1000.0));
        productManager.addProduct(new Product("Smartphone", 700.0));
        productManager.addProduct(new Product("Tablet", 300.0));

        List<Product> expensiveProducts = productManager.getProductsAbovePrice(700.0);
        assertEquals(1, expensiveProducts.size());
        assertTrue(expensiveProducts.stream().anyMatch(p -> p.getName().equals("Laptop")));
    }

    @Test
    public void testGetProductsAbovePriceWithNegativePrice() {
        productManager.addProduct(new Product("Laptop", 1000.0));
        productManager.addProduct(new Product("Smartphone", 700.0));
        productManager.addProduct(new Product("Tablet", 300.0));

        List<Product> expensiveProducts = productManager.getProductsAbovePrice(-100.0);
        assertEquals(3, expensiveProducts.size());
    }

    @Test
    public void testGetProductWithMultipleEntries() {
        Product product1 = new Product("Laptop", 1000.0);
        Product product2 = new Product("Laptop", 1500.0);
        productManager.addProduct(product1);
        productManager.addProduct(product2);

        Product retrievedProduct = productManager.getProduct("Laptop");
        assertEquals(1000.0, retrievedProduct.getPrice()); // First matching product should be returned
    }

    @Test
    public void testGetProductsAbovePriceWithAllBelowPrice() {
        productManager.addProduct(new Product("Laptop", 500.0));
        productManager.addProduct(new Product("Smartphone", 300.0));
        productManager.addProduct(new Product("Tablet", 200.0));

        List<Product> expensiveProducts = productManager.getProductsAbovePrice(600.0);
        assertEquals(0, expensiveProducts.size());
    }

    @Test
    public void testGetProductsAbovePriceWithAllNull() {
        productManager.addProduct(null);
        productManager.addProduct(null);
        productManager.addProduct(null);

        List<Product> expensiveProducts = productManager.getProductsAbovePrice(600.0);
        assertEquals(0, expensiveProducts.size());
    }

    @Test
    public void testGetProductAllBranches() {
        Product product1 = new Product("Laptop", 1000.0);
        Product product2 = new Product("Smartphone", 700.0);
        Product product3 = new Product("Tablet", 300.0);

        productManager.addProduct(product1);
        productManager.addProduct(product2);
        productManager.addProduct(product3);

        assertNull(productManager.getProduct(null)); // Cover null name branch
        assertNotNull(productManager.getProduct("Laptop")); // Cover name found branch
        assertNull(productManager.getProduct("Nonexistent")); // Cover name not found branch
    }

    @Test
    public void testGetProductsAbovePriceAllBranches() {
        Product product1 = new Product("Laptop", 1000.0);
        Product product2 = new Product("Smartphone", 700.0);
        Product product3 = new Product("Tablet", 300.0);
        Product product4 = null;

        productManager.addProduct(product1);
        productManager.addProduct(product2);
        productManager.addProduct(product3);
        productManager.addProduct(product4);

        List<Product> result = productManager.getProductsAbovePrice(600.0);
        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));

        // 추가 테스트: Product가 null일 때
        result = productManager.getProductsAbovePrice(1000.0);
        assertEquals(1, result.size());
        assertTrue(result.contains(product1));
    }

    @Test
    public void testGetTotalPriceAllBranches() {
        Product product1 = new Product("Laptop", 1000.0);
        Product product2 = new Product("Smartphone", 700.0);
        Product product3 = new Product("Tablet", 300.0);
        Product product4 = null;

        productManager.addProduct(product1);
        productManager.addProduct(product2);
        productManager.addProduct(product3);
        productManager.addProduct(product4);

        double totalPrice = productManager.getTotalPrice();
        assertEquals(2000.0, totalPrice); // Null product should not affect the total price

        // 추가 테스트: Product가 null일 때
        productManager.addProduct(null);
        totalPrice = productManager.getTotalPrice();
        assertEquals(2000.0, totalPrice); // Null product should not affect the total price
    }
}


