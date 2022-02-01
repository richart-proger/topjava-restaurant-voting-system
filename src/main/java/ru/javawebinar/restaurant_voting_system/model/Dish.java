package ru.javawebinar.restaurant_voting_system.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name", "date"}, name = "dishes_unique_name_date_rest_idx")})
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    private LocalDate date;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish() {

    }

    public Dish(Dish d) {
        this(d.id, d.name, d.price, d.date, d.restaurant);
    }

    public Dish(Integer id, String name, Integer price, LocalDate date, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
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