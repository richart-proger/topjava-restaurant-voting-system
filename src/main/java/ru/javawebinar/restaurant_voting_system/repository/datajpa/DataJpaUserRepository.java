package ru.javawebinar.restaurant_voting_system.repository.datajpa;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.restaurant_voting_system.model.User;
import ru.javawebinar.restaurant_voting_system.repository.UserRepository;

import java.util.List;

@Repository
public class DataJpaUserRepository implements UserRepository {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaUserRepository(CrudUserRepository crudUserRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.delete(id) != 0;
    }

    @Override
    public User getByEmail(String email) {
        return crudUserRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.findAll(SORT_NAME_EMAIL);
    }

    @Override
    @Transactional
    public User getWithVotes(int id) {
        Hibernate.initialize(crudRestaurantRepository.findAll());
        return crudUserRepository.getWithVotes(id);
    }
}