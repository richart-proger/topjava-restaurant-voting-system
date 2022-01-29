package ru.javawebinar.restaurant_voting_system.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId"),
        @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT v FROM Vote v ORDER BY v.bookingDate DESC"),
        @NamedQuery(name = Vote.ALL_BY_USER_ID_SORTED, query = "SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.bookingDate DESC"),
        @NamedQuery(name = Vote.GET_BETWEEN, query = """
                    SELECT v FROM Vote v 
                    WHERE v.user.id=:userId AND v.bookingDate >= :startDate AND v.bookingDate <= :endDate ORDER BY v.bookingDate DESC
                """)
})

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "booking_date"}, name = "votes_unique_booking_idx")})
public class Vote extends AbstractBaseEntity {

    public static final String DELETE = "Vote.delete";
    public static final String ALL_SORTED = "Vote.getAllSorted";
    public static final String ALL_BY_USER_ID_SORTED = "Vote.getAllByUserIdSorted";
    public static final String GET_BETWEEN = "Vote.getBetween";

    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "global_seq", foreignKeyDefinition = "START WITH 100000"))
    @OneToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @JoinColumn(name = "restaurant_id", nullable = false, foreignKey = @ForeignKey(name = "global_seq", foreignKeyDefinition = "START WITH 100000"))
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "booking_date", nullable = false, columnDefinition = "date default now()")
    private LocalDate bookingDate;

    public Vote() {

    }

    public Vote(Vote v) {
        this(v.id, v.user, v.restaurant, v.bookingDate);
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate bookingDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
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