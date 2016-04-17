package belight;

import java.util.LinkedList;
import java.util.List;
import com.amazon.speech.speechlet.Session;
/**
 * Created 4/16/16. Description...
 *
 * @author Andrey Chergik <achergik@gmail.com>
 */
public class SessionHelper {
    private static Integer caloriesMax = 3000;

    public static final String CURRENT_INTAKE_CALORIES = "CURRENT_INTAKE_CALORIES";
    public static final String CURRENT_INTAKE_FOOD_NAMES = "CURRENT_INTAKE_FOOD_NAMES";

    public static Integer getCurrentIntakeCalories(Session session) {
        return (Integer) session.getAttribute(CURRENT_INTAKE_CALORIES);
    }

    public static List<String> getCurrentIntakeFoodNames(Session session) {
        return (List<String>) session.getAttribute(CURRENT_INTAKE_FOOD_NAMES);
    }

    public static void addCurrentInTake(Session session, FoodItem foodItem) {
        Integer calTaken = getCurrentIntakeCalories(session);
        if(calTaken == null) {
            calTaken = 0;
        }
        session.setAttribute(CURRENT_INTAKE_CALORIES, calTaken + foodItem.getCalories());

        List<String> foodTakens = getCurrentIntakeFoodNames(session);
        if(foodTakens ==  null) {
            foodTakens =  new LinkedList<>();
        }
        session.setAttribute(CURRENT_INTAKE_FOOD_NAMES, foodItem.getName());
    }

    public static int getResidualInCalories(Session session, FoodItem foodItem) {
        Integer calTaken = getCurrentIntakeCalories(session);

        if(calTaken == null) {
            calTaken = 0;
        }

        return caloriesMax - (calTaken + foodItem.getCalories());
    }

    public static String getUserName() {
        return "Neo";
    }
}