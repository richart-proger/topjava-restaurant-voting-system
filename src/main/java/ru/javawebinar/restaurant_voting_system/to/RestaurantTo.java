package ru.javawebinar.restaurant_voting_system.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

public class RestaurantTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private final String restaurantName;

    @ConstructorProperties({"id", "name"})
    public RestaurantTo(Integer id, String name) {
        super(id);
        this.restaurantName = name;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}