package ru.javawebinar.restaurant_voting_system.to;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

public class DishTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    @ApiModelProperty(example = "New food", position = 1)
    private final String dishName;

    @NotNull
    @Range(min = 10, max = 5000)
    @ApiModelProperty(example = "888", position = 2)
    private final Integer price;

    @ConstructorProperties({"id", "dishName", "price"})
    public DishTo(Integer id, String dishName, Integer price) {
        super(id);
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