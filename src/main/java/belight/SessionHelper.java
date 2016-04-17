package belight;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.amazon.speech.speechlet.Session;
/**
 * Created 4/16/16. Description...
 *
 * @author Neo Li. <neo.siqi.li@hotmail.com>
 */
public class SessionHelper {
    public static Integer caloriesMax = 3000;

    public static final String CURRENT_INTAKE_CALORIES = "CURRENT_INTAKE_CALORIES";
    public static final String CURRENT_INTAKE_FOOD_NAMES = "CURRENT_INTAKE_FOOD_NAMES";

    public static Integer getCurrentIntakeCalories(Session session) {
        Integer currentIntake = (Integer) session.getAttribute(CURRENT_INTAKE_CALORIES);

        if (currentIntake == null) {
            return 0;
        }

        return currentIntake;
    }

    public static String getCurrentIntakeFoodNames(Session session) {
        return (String) session.getAttribute(CURRENT_INTAKE_FOOD_NAMES);
    }

    public static void addCurrentInTake(Session session, FoodItem foodItem) {
        Integer calTaken = getCurrentIntakeCalories(session);
        if(calTaken == null) {
            calTaken = 0;
        }
        session.setAttribute(CURRENT_INTAKE_CALORIES, calTaken + foodItem.getCalories());

        String foodTakens = getCurrentIntakeFoodNames(session);
        if(foodTakens ==  null) {
            foodTakens = "";
        }
        foodTakens = foodItem.getName() + foodTakens;
        session.setAttribute(CURRENT_INTAKE_FOOD_NAMES, foodTakens);
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
