package beatalbumshop.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStatus {
    public static final long PENDING = 0;
    public static final long PROCESSING = 1;
    public static final long SHIPPED = 2;
    public static final long DELIVERED = 3;
    public static final long CANCELLED = 4;
    public static final long RETURNED = 5;
    
    private static final Map<String, Long> STATUS_MAP = new HashMap<>();
    
    static {
        STATUS_MAP.put("Pending", PENDING);
        STATUS_MAP.put("Processing", PROCESSING);
        STATUS_MAP.put("Shipped", SHIPPED);
        STATUS_MAP.put("Delivered", DELIVERED);
        STATUS_MAP.put("Cancelled", CANCELLED);
        STATUS_MAP.put("Returned", RETURNED);
    }

    public static List<String> getStatusNames() {
        return Arrays.asList("Pending", "Processing", "Shipped", "Delivered", "Cancelled", "Returned");
    }

    public static long getLongValueByName(String name) {
        return STATUS_MAP.get(name);
    }
    
    public static String getStatusNameByLongValue(long value) {
        for (Map.Entry<String, Long> entry : STATUS_MAP.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
