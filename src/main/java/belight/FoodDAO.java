package belight;

import java.util.Map;
import java.util.HashMap;

/**
 * Created 4/16/16. Description...
 *
 * @author Neo Li. <neo.siqi.li@hotmail.com>
 */
public class FoodDao {

    // If have time replace with Dynamo DB.
    public final static Map<String, FoodItem> foodItems = new HashMap<>();

    static {
        foodItems.put("APPLE", new FoodItem("Apple", 95, false, "Prime Now"));
        foodItems.put("PIZZA", new FoodItem("Pizza", 2269, true, "Bite Squad"));
        foodItems.put("BURGER", new FoodItem("Burger", 563, false, "Prime Now"));
        foodItems.put("SALAD", new FoodItem("Salad", 94, false, "Prime Now"));
    }

    public final static FoodItem findByName(String name) {
        return foodItems.get(name.toUpperCase());
    }
}
