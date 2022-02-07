package ru.javawebinar.restaurant_voting_system.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.ConstructorProperties;
import java.util.List;

public class RestaurantTo {

    @JsonProperty(value = "restaurant_id")
    private Integer restaurantId;

    @JsonProperty(value = "restaurant_name")
    private String restaurantName;

    @JsonProperty(value = "menu")
    private List<MenuTo> restaurantMenu;

    public RestaurantTo() {
    }

    @ConstructorProperties({"restaurant_id", "restaurant_name", "menu"})
    public RestaurantTo(Integer restaurantId, String restaurantName, List<MenuTo> restaurantMenu) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantMenu = restaurantMenu;
    }

    @ConstructorProperties({"restaurant_id", "restaurant_name"})
    public RestaurantTo(Integer restaurantId, String restaurantName) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
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

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<MenuTo> getRestaurantMenu() {
        return restaurantMenu;
    }

    public void setRestaurantMenu(List<MenuTo> restaurantMenu) {
        this.restaurantMenu = restaurantMenu;
    }
}