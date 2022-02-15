package ru.javawebinar.restaurant_voting_system.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.LocalDate;

public class VoteTo extends BaseTo {

    private final Integer restaurantId;

    @NotBlank
    @Size(min = 2, max = 100)
    private final String restaurantName;

    private final LocalDate bookingDate;

    @ConstructorProperties({"id", "restaurantId", "restaurantName", "bookingDate"})
    public VoteTo(Integer id, Integer restaurantId, String restaurantName, LocalDate bookingDate) {
        super(id);
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.bookingDate = bookingDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }
}