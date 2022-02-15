package ru.javawebinar.restaurant_voting_system.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.List;

public class MenuTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private final String restaurantName;

    private final LocalDate menuDate;

    @NotNull
    private final List<DishTo> menu;

    @ConstructorProperties({"id", "name", "restaurantName", "date"})
    public MenuTo(Integer id, String name, List<DishTo> restaurantName, LocalDate date) {
        super(id);
        this.restaurantName = name;
        this.menu = restaurantName;
        this.menuDate = date;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public List<DishTo> getMenu() {
        return menu;
    }

    public LocalDate getMenuDate() {
        return menuDate;
    }
}