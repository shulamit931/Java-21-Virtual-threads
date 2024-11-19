import java.util.Date;
import java.util.Map;

public record UserWithPurchases(User user, Map<Date, Double> purchases) {
}
