package ru.javawebinar.restaurant_voting_system.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {

    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "date");

    private final CrudDishRepository crudDishRepository;

    public DataJpaDishRepository(CrudDishRepository crudDishRepository) {
        this.crudDishRepository = crudDishRepository;
    }

    @Override
    public Dish getByRestaurantId(int id, int restaurantId) {
        return crudDishRepository.findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public Dish get(int id) {
        return crudDishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish save(Dish dish) {
        return crudDishRepository.save(dish);
    }

    @Override
    public boolean delete(int id) {
        return crudDishRepository.delete(id) != 0;
    }

    @Override
    public List<Dish> getAll() {
        return crudDishRepository.findAll(SORT_DATE);
    }

    @Override
    public List<Dish> getDish(int restaurantId, LocalDate date) {
        return crudDishRepository.getDish(restaurantId, date);
    }

    @Override
    public List<Dish> getDishByRestaurantIdBetweenPeriod(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return crudDishRepository.getDishByRestaurantIdBetweenPeriod(startDate, endDate, restaurantId);
    }

    @Override
    public List<Dish> getAllDishByRestaurantId(int restaurantId) {
        return crudDishRepository.getAllDishByRestaurantId(restaurantId);
    }

    @Override
    public List<Dish> getAllDishBetweenPeriod(LocalDate startDate, LocalDate endDate) {
        return crudDishRepository.getAllDishBetweenPeriod(startDate, endDate);
    }

    @Override
    public Dish getWithRestaurant(int id, int userId) {
        return crudDishRepository.getWithRestaurant(id, userId);
    }
}