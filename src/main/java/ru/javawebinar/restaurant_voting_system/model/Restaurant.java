package ru.javawebinar.restaurant_voting_system.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @OrderBy("date desc")
    private List<Dish> restaurantMenu;

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @OrderBy("bookingDate DESC")
    private List<Vote> votes;

    public Restaurant(List<Dish> menu) {
        this.restaurantMenu = menu;
    }

    public Restaurant() {

    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, List<Dish> menu) {
        super(id, name);
        setMenu(menu);
    }

    public Restaurant(Restaurant r) {
        this(r.id, r.name, r.restaurantMenu);
    }

    public List<Dish> getMenu() {
        return restaurantMenu;
    }

    public void setMenu(List<Dish> menu) {
        this.restaurantMenu = menu;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Restaurant{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}