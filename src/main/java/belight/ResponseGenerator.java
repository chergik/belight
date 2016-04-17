package belight;

import com.amazon.speech.speechlet.Session;

/**
 * Created 4/16/16. Description...
 *
 * @author Andrey Chergik <achergik@gmail.com>
 */
public class ResponseGenerator {
    public static String getResponse(Session session, FoodItem foodItem) {
        final int residualInCalories = SessionHelper.getResidualInCalories(session, foodItem);

        if (residualInCalories <= 0) {
            return String.format("You are over taken %d calories by eating %s.", foodItem
                    .getCalories(), foodItem.getName());
        } else {
            return String.format("You eat %s. And it is %d calories. You have %d calories to go. " +
                                         "Keep it up.", foodItem.getName(),
                                 foodItem.getCalories(), residualInCalories);
        }
    }
}
