import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopService {
    private final ProductRepo productRepo = new ProductRepo();
    private final OrderRepo orderRepo = new OrderMapRepo();

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Product productToOrder = productRepo.getProductById(productId);
            if (productToOrder == null) {
                System.out.println("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
                return null;
            }
            products.add(productToOrder);
        }

        Order newOrder = new Order(UUID.randomUUID().toString(), products, calculateTotalPrice(productIds));

        return orderRepo.addOrder(newOrder);
    }

    BigDecimal calculateTotalPrice(List<String> productIds) {
        BigDecimal totalPrice = new BigDecimal("0.00");
        for (String productId : productIds) {
            Product product = productRepo.getProductById(productId);
            if (product != null) {
                totalPrice = totalPrice.add(product.price());
            }
        }
        return totalPrice;
    }
}
