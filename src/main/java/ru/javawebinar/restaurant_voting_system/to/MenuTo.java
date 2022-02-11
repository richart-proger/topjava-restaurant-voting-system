package ru.javawebinar.restaurant_voting_system.to;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.List;

public class MenuTo {

    private Integer restaurantId;

    private String restaurantName;

    private LocalDate menuDate;

    private List<DishTo> menu;

    public MenuTo() {
    }

    @ConstructorProperties({"id", "name", "restaurantName", "date"})
    public MenuTo(Integer id, String name, List<DishTo> restaurantName, LocalDate date) {
        this.restaurantId = id;
        this.restaurantName = name;
        this.menu = restaurantName;
        this.menuDate = date;
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