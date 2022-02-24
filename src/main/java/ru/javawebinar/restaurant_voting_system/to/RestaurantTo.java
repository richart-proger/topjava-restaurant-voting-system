package ru.javawebinar.restaurant_voting_system.to;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

public class RestaurantTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    @ApiModelProperty(example = "Unique restaurant")
    private final String name;

    @ConstructorProperties({"id", "name"})
    public RestaurantTo(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getRestaurantName() {
        return name;
    }
}