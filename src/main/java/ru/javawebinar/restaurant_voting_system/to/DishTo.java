package ru.javawebinar.restaurant_voting_system.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

public class DishTo {

    @JsonProperty(value = "dish_id")
    private Integer dishId;

    @JsonProperty(value = "dish_name")
    private String dishName;

    @JsonProperty(value = "dish_price")
    private Integer price;

    @JsonIgnore
    private LocalDate date;

    @JsonIgnore
    private Restaurant restaurant;

    public DishTo() {
    }

    @ConstructorProperties({"dish_id", "dish_name", "dish_price", "date", "restaurant"})
    public DishTo(Integer dishId, String dishName, Integer price, LocalDate date, Restaurant restaurant) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}