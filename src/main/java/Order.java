import java.math.BigDecimal;
import java.util.List;

public record Order(
        String id,
        List<Product> products,
        BigDecimal total
) {
}
