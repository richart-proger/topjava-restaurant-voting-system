package ru.javawebinar.restaurant_voting_system.to;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private final String dishName;

    @NotNull
    @Range(min = 10, max = 5000)
    private final Integer price;

    @ConstructorProperties({"id", "name", "price"})
    public DishTo(Integer dishId, String dishName, Integer price) {
        super(dishId);
        this.dishName = dishName;
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public Integer getPrice() {
        return price;
    }
}