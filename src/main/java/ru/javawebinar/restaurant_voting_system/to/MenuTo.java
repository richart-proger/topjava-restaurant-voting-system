package ru.javawebinar.restaurant_voting_system.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.List;

public class MenuTo {

    @JsonProperty(value = "restaurant_id")
    private Integer restaurantId;

    @JsonProperty(value = "restaurant_name")
    private String restaurantName;

    @JsonIgnore
    private LocalDate menuDate;

    @JsonProperty(value = "menu")
    private List<DishTo> menu;

    public MenuTo() {
    }

    @ConstructorProperties({"restaurant_id", "restaurant_name", "menu", "date"})
    public MenuTo(Integer restaurantId, String restaurantName, List<DishTo> menu, LocalDate menuDate) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.menu = menu;
        this.menuDate = menuDate;
    }

    @ConstructorProperties({"restaurant_id", "restaurant_name", "date"})
    public MenuTo(Integer restaurantId, String restaurantName, LocalDate menuDate) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.menuDate = menuDate;
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

    public List<DishTo> getMenu() {
        return menu;
    }

    public void setMenu(List<DishTo> menu) {
        this.menu = menu;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(LocalDate menuDate) {
        this.menuDate = menuDate;
    }
}