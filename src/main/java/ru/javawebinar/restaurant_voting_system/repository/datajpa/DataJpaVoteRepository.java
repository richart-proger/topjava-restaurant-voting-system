package ru.javawebinar.restaurant_voting_system.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private static final Sort SORT_DATE = Sort.by(Sort.Direction.DESC, "bookingDate");

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository, CrudUserRepository crudUserRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Vote get(int id, int userId) {
        return crudVoteRepository.findById(id)
                .filter(vote -> vote.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    @Transactional
    public Vote save(Vote vote, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(crudUserRepository.getById(userId));
        return crudVoteRepository.save(vote);
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.findAll(SORT_DATE);
    }

    @Override
    public List<Vote> getAllByUserId(int userId) {
        return crudVoteRepository.getAllByUserId(userId);
    }

    @Override
    public List<Vote> getBetweenPeriod(LocalDate startDate, LocalDate endDate, int userId) {
        return crudVoteRepository.getByUserIdBetweenPeriod(startDate, endDate, userId);
    }

    @Override
    public Vote getWithUser(int id, int userId) {
        return crudVoteRepository.getWithUser(id, userId);
    }

    @Override
    public Vote getWithRestaurant(int id, int restaurantId) {
        return crudVoteRepository.getWithRestaurant(id, restaurantId);
    }
}