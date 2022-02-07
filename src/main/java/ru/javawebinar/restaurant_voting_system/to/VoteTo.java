package ru.javawebinar.restaurant_voting_system.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;
import ru.javawebinar.restaurant_voting_system.model.User;

import java.beans.ConstructorProperties;
import java.time.LocalDate;

public class VoteTo {

    @JsonProperty(value = "vote_id")
    private Integer id;

    @JsonProperty(value = "vote_user")
    private User user;

    @JsonProperty(value = "vote_restaurant")
    private Restaurant restaurant;

    @JsonProperty(value = "vote_booking_date")
    private LocalDate bookingDate;

    public VoteTo() {
    }

    @ConstructorProperties({"vote_id", "vote_user", "vote_restaurant", "vote_booking_date"})
    public VoteTo(Integer id, User user, Restaurant restaurant, LocalDate bookingDate) {
        this.id = id;
        this.user = user;
        this.restaurant = restaurant;
        this.bookingDate = bookingDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}