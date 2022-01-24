package ru.javawebinar.restaurant_voting_system.model;

import javax.persistence.*;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r")
})

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    public static final String DELETE = "Restaurant.delete";
    public static final String ALL_SORTED = "Restaurant.getAllSorted";

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @OrderBy("date desc")
    private Set<Dish> restaurantMenu;

    public Restaurant(Set<Dish> menu) {
        this.restaurantMenu = menu;
    }

    public Restaurant() {

    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, Set<Dish> menu) {
        super(id, name);
        setMenu(menu);
    }

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.restaurantMenu);
    }

    public Set<Dish> getMenu() {
        return restaurantMenu;
    }

    public void setMenu(Set<Dish> menu) {
        this.restaurantMenu = menu;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Restaurant{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", restaurantMenu=").append(restaurantMenu);
        sb.append('}');
        return sb.toString();
    }
}