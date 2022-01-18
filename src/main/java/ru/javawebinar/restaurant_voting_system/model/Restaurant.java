package ru.javawebinar.restaurant_voting_system.model;

import java.util.HashSet;
import java.util.Set;

public class Restaurant extends AbstractNamedEntity {
    private Set<Dish> restaurantMenu = new HashSet<>();

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.restaurantMenu);
    }

    public Restaurant(Integer id, String name, Set<Dish> menu) {
        super(id, name);
        setMenu(menu);
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
