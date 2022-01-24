package ru.javawebinar.restaurant_voting_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.repository.DishRepository;

import java.util.List;

import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final DishRepository repository;

    @Autowired
    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish create(Dish dish) {
        return repository.save(dish);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Dish> getAll() {
        return repository.getAll();
    }

    public void update(Dish dish) {
        checkNotFoundWithId(repository.save(dish), dish.getId());
    }
}