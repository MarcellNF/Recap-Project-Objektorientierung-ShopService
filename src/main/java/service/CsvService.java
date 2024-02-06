package service;

import product.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CsvService {

    private CsvService() {
    }

    public static List<Product> readProductsFromCsv(String filePath) {
        final List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String id = values[0];
                String name = values[1];
                BigDecimal price = new BigDecimal(values[2]);
                int stock = Integer.parseInt(values[3]);
                products.add(new Product(id, name, price, stock));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return products;
    }
}