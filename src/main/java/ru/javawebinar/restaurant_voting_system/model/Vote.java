package ru.javawebinar.restaurant_voting_system.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "booking_date"}, name = "votes_unique_booking_idx")})
public class Vote extends AbstractBaseEntity {

    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "global_seq", foreignKeyDefinition = "START WITH 100000"))
    @ManyToOne(fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference
    private User user;

    @Column(name = "user_email")
    private String email;

    @JsonIgnore
    @JoinColumn(name = "restaurant_id", nullable = false, foreignKey = @ForeignKey(name = "global_seq", foreignKeyDefinition = "START WITH 100000"))
    @ManyToOne(fetch = FetchType.EAGER)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "booking_date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate bookingDate;

    public Vote() {

    }

    public Vote(Vote v) {
        this(v.id, v.user, v.restaurant, v.bookingDate);
        this.email = user.getEmail();
        this.restaurantName = restaurant.getName();
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate bookingDate) {
        super(id);
        this.user = user;
        this.email = user.getEmail();
        this.restaurant = restaurant;
        this.restaurantName = restaurant.getName();
        this.bookingDate = bookingDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail() {
        this.email = user.getEmail();
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName() {
        this.restaurantName = restaurant.getName();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Vote{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", restaurant=").append(restaurant);
        sb.append(", bookingDate=").append(bookingDate);
        sb.append('}');
        return sb.toString();
    }
}