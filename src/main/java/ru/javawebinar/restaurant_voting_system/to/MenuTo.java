package ru.javawebinar.restaurant_voting_system.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.List;

public class MenuTo extends BaseTo {

    private static final String DISHES = "" +
            "[{\"dishName\": \"Some food 1\", \"price\": 777}, " +
            "{\"dishName\": \"Some food 2\", \"price\": 555}]";

    @NotBlank
    @Size(min = 2, max = 100)
    @ApiModelProperty(example = "Mexican restaurant", position = 1)
    private final String restaurantName;

    @JsonIgnore
    private final LocalDate date;

    @NotNull
    @ApiModelProperty(example = DISHES)
    private final List<DishTo> menu;

    @ConstructorProperties({"id", "restaurantName", "menu", "date"})
    public MenuTo(Integer id, String restaurantName, List<DishTo> menu, LocalDate date) {
        super(id);
        this.restaurantName = restaurantName;
        this.menu = menu;
        this.date = date;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public List<DishTo> getMenu() {
        return menu;
    }

    public LocalDate getMenuDate() {
        return date;
    }
}