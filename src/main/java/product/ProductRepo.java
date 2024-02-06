package product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepo {
    private final Map<String, Product> products;

    public ProductRepo() {
        products = new HashMap<>();
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProductById(String id) {
        for (Product product : products.values()) {
            if (product.id().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product addProduct(Product newProduct) {
        products.put(newProduct.id(), newProduct);
        return newProduct;
    }

    public void addProducts(List<Product> newProducts) {
        for (Product product : newProducts) {
            products.put(product.id(), product);
        }
    }

    public void removeProduct(String id) {
        products.remove(id);
    }
}
