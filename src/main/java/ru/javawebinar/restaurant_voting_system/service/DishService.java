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
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "Dish must be not null");
        return repository.save(dish, restaurantId);
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    @Cacheable("dishes")
    public List<Dish> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "dishes", allEntries = true)
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "Dish must be not null");
        checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }

    public List<Dish> getMenuByDate(int restaurantId, LocalDate date) {
        return checkNotFoundWithId(repository.getMenu(restaurantId, date), restaurantId);
    }

    public List<Dish> getMenuByRestaurantIdBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int restaurantId) {
        return repository.getMenuByRestaurantIdBetweenPeriod(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), restaurantId);
    }

    public List<Dish> getAllMenusBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        return repository.getAllMenusBetweenPeriod(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate));
    }
}