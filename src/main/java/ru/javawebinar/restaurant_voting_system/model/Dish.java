package ru.javawebinar.restaurant_voting_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name", "date"}, name = "dishes_unique_name_date_rest_idx")})
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 10, max = 5000)
    @NotNull
    private Integer price;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date;

    @JsonIgnore
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "restaurant_name")
    private String restaurantName;

    public Dish() {

    }

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }

    public Dish(Dish d) {
        this(d.id, d.name, d.price, d.date, d.restaurant);
        this.restaurantName = restaurant.getName();
    }

    public Dish(Integer id, String name, Integer price, LocalDate date, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
        this.restaurantName = restaurant.getName();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName() {
        this.restaurantName = restaurant.getName();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Dish{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", date=").append(date);
        sb.append(", restaurant=").append(restaurant);
        sb.append('}');
        return sb.toString();
    }
}