package ru.javawebinar.restaurant_voting_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;
import ru.javawebinar.restaurant_voting_system.repository.RestaurantRepository;
import ru.javawebinar.restaurant_voting_system.to.RestaurantTo;

import java.util.List;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.updateFromRestaurantTo;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "Restaurant must be not null");
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(RestaurantTo restaurantTo) {
        Assert.notNull(restaurantTo, "Restaurant must be not null");
        Restaurant restaurant = get(restaurantTo.getId());
        updateFromRestaurantTo(restaurant, restaurantTo);
    }

    public Restaurant getWithDishes(int id) {
        return checkNotFoundWithId(repository.getWithDishes(id), id);
    }

    public Restaurant getWithVotes(int id) {
        return checkNotFoundWithId(repository.getWithVotes(id), id);
    }
}