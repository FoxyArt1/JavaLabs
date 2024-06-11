package org.example;

import org.example.exceptions.ProductNotFoundException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        Product product1 = new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics);
        Product product2 = new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном", smartphones);
        Product product3 = new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories);

        Store storeInstance = new Store();
        storeInstance.addProductToWarehouse(product1);
        storeInstance.addProductToWarehouse(product2);
        storeInstance.addProductToWarehouse(product3);

        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Виберіть опцію.\n1. Переглянути список товарів\n2. Шукати товар за категорією\n3. Шукати товар за id\n4. Додати товар до кошика\n5. Переглянути кошик\n6. Зробити замовлення\n7. Вилучити товар з кошика\n8. Переглянути список замовлень\n9. Вийти");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> System.out.println(storeInstance.getAllProducts());
                case 2 -> {
                    System.out.println("Введіть назву Категорії");
                    String categoryName = scanner.next();
                    try {
                        System.out.println(storeInstance.getProductsByCategoryName(categoryName));
                    } catch (ProductNotFoundException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("Введіть id товару");
                    int productId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        System.out.println(storeInstance.getProductById(productId));
                    } catch (ProductNotFoundException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                case 4 -> {
                    System.out.println("Введіть назву товару для додавання до кошика");
                    int productId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        Product product = storeInstance.getProductById(productId);
                        cart.addProduct(product);
                        System.out.println("Товар додано до кошика: " + product);
                    } catch (ProductNotFoundException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                case 5 -> System.out.println(cart);
                case 6 -> {
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Додайте товари до кошика перед замовленням.");
                    } else {
                        Order order = new Order(cart);
                        storeInstance.addOrder(order);
                        cart.clear();
                        System.out.println("Замовлення створено:\n" + order);
                    }
                }
                case 7 -> {
                    System.out.println("Введіть id товару для вилучення з кошика");
                    int productId = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        Product product = storeInstance.getProductById(productId);
                        if (cart.getProducts().contains(product)) {
                            cart.removeProduct(product);
                            System.out.println("Товар вилучено з кошика: " + product);
                        } else {
                            System.out.println("Товар не знайдено у кошику.");
                        }
                    } catch (ProductNotFoundException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                case 8 -> System.out.println(storeInstance.getOrdersHistory());
                case 9 -> {
                    System.out.println("Вихід з програми.");
                    System.exit(0);
                }
                default -> System.out.println("Некоректний вибір. Спробуйте ще раз.");
            }
        }
    }
}
