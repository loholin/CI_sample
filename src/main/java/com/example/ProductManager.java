package com.example;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private final List<Product> products;

    public ProductManager() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        products.add(product);
    }

    public Product getProduct(String name) {
        if (name == null) {
            return null;
        }
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProductsAbovePrice(double price) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product != null && product.getPrice() > price) {
                result.add(product);
            }
        }
        return result;
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Product product : products) {
            if (product != null) {
                total += product.getPrice();
            }
        }
        return total;
    }
}

