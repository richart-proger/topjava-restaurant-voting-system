package ru.javawebinar.restaurant_voting_system.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {

    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "date");

    private final CrudDishRepository crudDishRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaDishRepository(CrudDishRepository crudDishRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return crudDishRepository.findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.getById(restaurantId));
        return crudDishRepository.save(dish);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudDishRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public List<Dish> getAll() {
        return crudDishRepository.findAll(SORT_DATE);
    }

    @Override
    public List<Dish> getMenu(int restaurantId, LocalDate date) {
        return crudDishRepository.getMenu(restaurantId, date);
    }

    @Override
    public List<Dish> getMenuByRestaurantIdBetweenPeriod(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return crudDishRepository.getMenuByRestaurantIdBetweenPeriod(startDate, endDate, restaurantId);
    }

    @Override
    public List<Dish> getAllMenusBetweenPeriod(LocalDate startDate, LocalDate endDate) {
        return crudDishRepository.getAllMenusBetweenPeriod(startDate, endDate);
    }
}