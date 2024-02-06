package service;

import order.Order;
import order.OrderMapRepo;
import order.OrderRepo;
import product.Product;
import product.ProductRepo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShopService {
    private final ProductRepo productRepo = new ProductRepo();
    private final OrderRepo orderRepo = new OrderMapRepo();
    private final IdService idService = new IdService();

    public List<Product> getProducts() {
        return productRepo.getProducts();
    }

    public Product getProduct(String id) {
        return productRepo.getProductById(id);
    }

    public Product addProduct(Product newProduct) {
        return productRepo.addProduct(newProduct);
    }

    public void removeProduct(String id) {
        productRepo.removeProduct(id);
    }

    public List<Order> getAllOrders() {
        return orderRepo.getOrders();
    }

    public Order getOrder(String id) {
        return orderRepo.getOrderById(id);
    }

    public Order addOrder(List<String> productIds) {
        if (verifyAndReduceProductStock(productIds)) return null;

        List<Product> products = retrieveProducts(productIds);

        final BigDecimal totalPrice = calculateTotalPrice(productIds);
        final String orderId = idService.generateId();
        final Order newOrder = new Order(orderId, products, totalPrice);

        return orderRepo.addOrder(newOrder);
    }

    private boolean verifyAndReduceProductStock(List<String> productIds) {
        for (String productId : productIds) {
            if (!isProductAvailable(productId)) {
                System.out.println("Product with id " + productId + " is not available");
                return true;
            }
            reduceProductStock(productId);
        }
        return false;
    }

    public void removeOrder(String id) {
        orderRepo.removeOrder(id);
    }

    public List<Product> addProductsFromCsv(String filePath) {
        List<Product> products = CsvService.readProductsFromCsv(filePath);
        return productRepo.addProducts(products);
    }

    private BigDecimal calculateTotalPrice(List<String> productIds) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Product> products = retrieveProducts(productIds);

        for (Product product : products) {
            totalPrice = totalPrice.add(product.price());
        }

        return totalPrice;
    }

    private List<Product> retrieveProducts(List<String> productIds) {
        final List<Product> products = new ArrayList<>();

        for (String productId : productIds) {
            Product product = getProduct(productId);
            if (product != null) {
                products.add(product);
            } else {
                System.out.println("Product with id " + productId + " not found");
            }
        }

        return products;
    }

    private boolean isProductAvailable(String productId) {
        return getProduct(productId).stock() > 0;
    }

    private void reduceProductStock(String productId) {
        Product product = getProduct(productId);
        Product updatedProduct = new Product(product.id(), product.name(), product.price(), product.stock() - 1);
        productRepo.addProduct(updatedProduct);
    }
}
