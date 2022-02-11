package ru.javawebinar.restaurant_voting_system.web.rest.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.service.VoteService;
import ru.javawebinar.restaurant_voting_system.to.VoteTo;

import java.util.List;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getVoteTo;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getVoteTos;

public class AbstractVoteRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService voteService;

    public List<Vote> getAll() {
        log.info("getAll votes");
        return voteService.getAll();
    }

    public List<VoteTo> getAllWithAuthUser(int authUserId) {
        return getVoteTos(voteService.getAllWithAuthUser(authUserId));
    }

    public VoteTo get(int id, int userId) {
        log.info("get vote by id={} for userId={}", id, userId);
        return getVoteTo(voteService.get(id, userId));
    }

    public void delete(int id, int authUserId) {
        log.info("delete vote with id={}", id);
        voteService.delete(id, authUserId);
    }
}