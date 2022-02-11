package ru.javawebinar.restaurant_voting_system.to;

import java.beans.ConstructorProperties;

public class RestaurantTo {

    private Integer restaurantId;

    private String restaurantName;

    public RestaurantTo() {
    }

    @ConstructorProperties({"id", "name"})
    public RestaurantTo(Integer id, String name) {
        this.restaurantId = id;
        this.restaurantName = name;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}