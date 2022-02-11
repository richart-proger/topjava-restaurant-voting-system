package ru.javawebinar.restaurant_voting_system.to;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

public class VoteTo {

    private Integer id;

    private Integer userId;

    private Integer restaurantId;

    private String restaurantName;

    private LocalDate bookingDate;

    public VoteTo() {
    }

    @ConstructorProperties({"id", "userId", "restaurantId", "restaurantName", "bookingDate"})
    public VoteTo(Integer id, Integer userId, Integer restaurantId, String restaurantName, LocalDate bookingDate) {
        this.id = id;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.bookingDate = bookingDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}