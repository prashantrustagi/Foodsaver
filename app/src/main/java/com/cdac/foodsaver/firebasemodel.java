package com.cdac.foodsaver;

public class firebasemodel {
    private String FoodType;
    private String ItemName;
    private String PerishTime;

    public firebasemodel()
    {
    }
    public firebasemodel(String FoodType, String ItemName, String PerishTime)
    {
        this.FoodType = FoodType;
        this.ItemName = ItemName;
        this.PerishTime = PerishTime;

    }

    public String getFoodType() {
        return FoodType;
    }

    public void setFoodType(String foodType) {
        FoodType = foodType;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getPerishTime() {
        return PerishTime;
    }

    public void setPerishTime(String perishTime) {
        PerishTime = perishTime;
    }
}
