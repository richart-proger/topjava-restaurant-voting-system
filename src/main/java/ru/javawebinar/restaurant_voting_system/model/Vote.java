package ru.javawebinar.restaurant_voting_system.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {
    private User user;

    private Restaurant restaurant;

    private LocalDate bookingDate;

    public Vote(Vote v) {
        this(v.id, v.user, v.restaurant, v.bookingDate);
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate bookingDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.bookingDate = bookingDate;
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
