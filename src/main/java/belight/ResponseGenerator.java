package belight;

import com.amazon.speech.speechlet.Session;

/**
 * Created 4/16/16. Description...
 *
 * @author Neo Li. <neo.siqi.li@hotmail.com>
 */
public class ResponseGenerator {
    public static String getInitialPrompt(Session session) {
        return "I don't know. What did you have so far?";
    }

    public static String getResponse(Session session, FoodItem foodItem) {
        final int residualInCalories = SessionHelper.getResidualInCalories(session, foodItem);

        if (residualInCalories <= 0) {
            return String.format("You ate %s.  %d calories over your goal. Stop it. American " +
                                         "say yes.",  foodItem.getName(), foodItem
                    .getCalories());
        else if(foodItem.isBadFood()) {
            return String.format("%s is bad. It is %d calories. Be careful. Be light");

        } else {
            return String.format("%s is %d calories. You are doing great." +
                                         "Be light", foodItem.getName(),
                                 foodItem.getCalories());
        }
    }

    public static String getWhatElseCanIEat(Session session) {
        String eatenAlready = SessionHelper.getCurrentIntakeFoodNames(session);
        int residualInCalories = SessionHelper.caloriesMax - SessionHelper.getCurrentIntakeCalories
                (session);

        String response = "You had eaten " + eatenAlready + ". Total intake calories are " +
                residualInCalories;

        // increase already eat too much, or can't find a suitable items.
        String recommendation = "Sorry, I don't know. I'll find out.";

        for(FoodItem item : FoodDAO.foodItems.values()) {
            if(!eatenAlready.contains(item.getName()) && residualInCalories + item.getCalories() <
                    SessionHelper.caloriesMax) {
                recommendation = "I recommend  " + item.getName() + ", which is " + item
                        .getCalories() + " calories. And you can buy it from " + item.getBuyAt()
                        + ". Do you want me to order?";
            }
        }

        return response + recommendation;
    }
}
