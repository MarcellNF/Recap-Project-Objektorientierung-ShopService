import service.ShopService;

import java.util.Scanner;

public class Main {
    private static final ShopService shopService = new ShopService();

    public static void main(String[] args) {
        String filePath = "src/main/resources/products.csv";
        System.out.println(shopService.addProductsFromCsv(filePath));
        Scanner scanner = new Scanner(System.in);
        scanner.close();
    }
}
