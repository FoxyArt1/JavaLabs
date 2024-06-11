package org.example;

import org.example.exceptions.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Store {
    private final List<Product> products;
    private final List<Order> orders;

    public Store() {
        this.products = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addProductToWarehouse(Product product) {
        products.add(product);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Product getProductById(int productID) throws ProductNotFoundException {
        Optional<Product> productOptional = products.stream()
                .filter(product -> product.getId() == productID)
                .findFirst();
        return productOptional.orElseThrow(
                () -> new ProductNotFoundException("Не знайдено продукт. Перевірте введені дані!"));
    }

    public List<Product> getProductsByCategoryName(String categoryName) throws ProductNotFoundException {
        var filteredProducts = products.stream()
                .filter(product -> product.getProductCategory().getName().equals(categoryName))
                .toList();

        if (filteredProducts.isEmpty()) {
            throw new ProductNotFoundException("Поки що немає продуктів в цій категорії " + categoryName);
        }

        return filteredProducts;
    }

    public String getAllProducts() {
        StringBuilder sb = new StringBuilder("Товари в наявності:\n");
        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }
        return sb.toString();
    }

    public String getOrdersHistory() {
        StringBuilder sb = new StringBuilder("Ваші замовлення:\n");
        for (Order order : orders) {
            sb.append(order.toString()).append("\n");
        }
        return sb.toString();
    }
}