package ru.javawebinar.restaurant_voting_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.repository.VoteRepository;

import java.util.Collection;

import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {
    private VoteRepository repository;

    @Autowired
    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote, int userId) {
        return repository.save(vote, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Vote> getAll() {
        return repository.getAll();
    }

    public Collection<Vote> getAllByUserId(int userId) {
        return repository.getAllByUserId(userId);
    }

    public void update(Vote vote, int userId) {
        checkNotFoundWithId(repository.save(vote, userId), vote.getId());
    }
}