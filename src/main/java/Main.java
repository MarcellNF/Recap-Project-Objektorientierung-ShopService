import order.Order;
import product.Product;
import service.IdService;
import service.ShopService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ShopService shopService = new ShopService();
    private static final Scanner scanner = new Scanner(System.in);;
    private static final IdService idService = new IdService();

    public static void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> listProducts();
                case 2 -> addProduct();
                case 3 -> listOrders();
                case 4 -> placeOrder();
                case 5 -> exit = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n=== Shop Management System ===");
        System.out.println("1. List Products");
        System.out.println("2. Add Product");
        System.out.println("3. List Orders");
        System.out.println("4. Place Order");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void listProducts() {
        System.out.println("\n=== Products ===");
        shopService.getProducts().forEach(product ->
                System.out.println(product.id() + ": " + product.name() + " - $" + product.price() + " (Stock: " + product.stock() + ")"));
    }

    private static void addProduct() {
        System.out.println("\n=== Add Product ===");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter initial stock quantity: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        final String productId = idService.generateId();
        Product newProduct = new Product(productId, name, BigDecimal.valueOf(price), stock);
        Product addedProduct = shopService.addProduct(newProduct);
        System.out.println("Product added successfully.");
        System.out.println(addedProduct.id() + ": " + addedProduct.name() + " - $" + addedProduct.price() + " (Stock: " + addedProduct.stock() + ")");
    }

    private static void listOrders() {
        System.out.println("\n=== Orders ===");
        shopService.getAllOrders().forEach(order ->
                System.out.println(order.id() + ": Total $" + order.total()));
    }

    private static void placeOrder() {
        System.out.println("\n=== Place Order ===");
        System.out.print("Enter comma-separated product IDs: ");
        String input = scanner.nextLine();
        String[] productIds = input.split(",");
        List<String> productIdList = Arrays.asList(productIds);

        Order newOrder = shopService.addOrder(productIdList);
        if (newOrder != null) {
            System.out.println("Order placed successfully. Order ID: " + newOrder.id());
        } else {
            System.out.println("Failed to place order. Please check product availability.");
        }
    }

    public static void main(String[] args) {
       start();;
    }
}
