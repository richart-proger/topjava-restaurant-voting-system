package ru.javawebinar.restaurant_voting_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.restaurant_voting_system.util.DateUtil.atStartOfDayOrMin;
import static ru.javawebinar.restaurant_voting_system.util.DateUtil.atStartOfNextDayOrMax;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final DishRepository repository;

    @Autowired
    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public Dish create(Dish dish) {
        Assert.notNull(dish, "Dish must be not null");
        return repository.save(dish);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Dish getByRestaurantId(int id, int restaurantId) {
        return checkNotFoundWithId(repository.getByRestaurantId(id, restaurantId), id);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("dishes")
    public List<Dish> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void update(Dish dish) {
        Assert.notNull(dish, "Dish must be not null");
        checkNotFoundWithId(repository.save(dish), dish.getId());
    }

    public List<Dish> getDishByDate(int restaurantId, LocalDate date) {
        return checkNotFoundWithId(repository.getDish(restaurantId, date), restaurantId);
    }

    public List<Dish> getDishByRestaurantIdBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int restaurantId) {
        return repository.getDishByRestaurantIdBetweenPeriod(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), restaurantId);
    }

    public List<Dish> getAllDishByRestaurantId(int restaurantId) {
        return repository.getAllDishByRestaurantId(restaurantId);
    }

    public List<Dish> getAllDishBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        return repository.getAllDishBetweenPeriod(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate));
    }

    public Dish getWithRestaurant(int id, int restaurantId) {
        return checkNotFoundWithId(repository.getWithRestaurant(id, restaurantId), id);
    }
}