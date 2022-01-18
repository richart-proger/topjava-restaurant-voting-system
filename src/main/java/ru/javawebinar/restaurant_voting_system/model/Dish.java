package ru.javawebinar.restaurant_voting_system.model;

import java.util.Date;

public class Dish extends AbstractNamedEntity {
    private Integer price;

    private Date date;

    private Restaurant restaurant;

    public Dish(Dish d) {
        this(d.id, d.name, d.price, d.date, d.restaurant);
    }

    public Dish(Integer id, String name, Integer price, Date date, Restaurant restaurant) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
