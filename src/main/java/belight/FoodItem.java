package belight;

/**
 * Created 4/16/16. Description...
 *
 * @author Neo Li. <neo.siqi.li@hotmail.com>
 */
public class FoodItem {
    private String name;
    private Integer calories;
    private String buyAt;
    private boolean badFood;

    public FoodItem(String name, int calories, boolean badFood, String buyAt) {
        this.name = name;
        this.calories = calories;
        this.buyAt = buyAt;
        this.badFood = badFood;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getBuyAt() {
        return buyAt;
    }

    public void setBuyAt(String buyAt) {
        this.buyAt = buyAt;
    }

    public boolean isBadFood() {
        return badFood;
    }

    public void setBadFood(boolean badFood) {
        this.badFood = badFood;
    }
}
