package ru.javawebinar.restaurant_voting_system.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQueries({
        @NamedQuery(name = Dish.DELETE, query = "DELETE FROM Dish d WHERE d.id=:id"),
        @NamedQuery(name = Dish.ALL_SORTED, query = "SELECT d FROM Dish d ORDER BY d.date DESC"),
        @NamedQuery(name = Dish.GET_MENU, query = "SELECT d FROM Dish d WHERE d.restaurant.id=:id AND d.date=:date ORDER BY d.date DESC"),
        @NamedQuery(name = Dish.GET_MENU_BY_RESTAURANT_ID_BETWEEN, query = """
                    SELECT d FROM Dish d 
                    WHERE d.restaurant.id=:restaurantId AND d.date >= :startDate AND d.date <= :endDate ORDER BY d.date DESC
                """),
        @NamedQuery(name = Dish.GET_ALL_MENUS_BETWEEN, query = """
                    SELECT d FROM Dish d 
                    WHERE d.date >= :startDate AND d.date <= :endDate ORDER BY d.date DESC
                """)
})

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name", "date"}, name = "dishes_unique_name_date_rest_idx")})
public class Dish extends AbstractNamedEntity {

    public static final String DELETE = "Dish.delete";
    public static final String ALL_SORTED = "Dish.getAllSorted";
    public static final String GET_MENU = "Dish.getMenu";
    public static final String GET_MENU_BY_RESTAURANT_ID_BETWEEN = "Dish.getMenuByRestaurantIdBetween";
    public static final String GET_ALL_MENUS_BETWEEN = "Dish.getAllMenusBetween";

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    private LocalDate date;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
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