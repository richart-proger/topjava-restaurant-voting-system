package ru.javawebinar.restaurant_voting_system.to;

import java.beans.ConstructorProperties;

public class DishTo {

    private Integer dishId;

    private String dishName;

    private Integer price;

    public DishTo() {
    }

    @ConstructorProperties({"id", "name", "price"})
    public DishTo(Integer dishId, String dishName, Integer price) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}