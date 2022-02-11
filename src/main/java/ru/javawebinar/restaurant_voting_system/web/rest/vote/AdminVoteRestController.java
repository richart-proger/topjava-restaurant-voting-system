package ru.javawebinar.restaurant_voting_system.web.rest.vote;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.service.VoteService;
import ru.javawebinar.restaurant_voting_system.to.VoteTo;

import java.util.List;

@RestController
@RequestMapping(value = AdminVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteRestController extends AbstractVoteRestController {

    public static final String REST_URL = "/rest/admin/votes";

    private final VoteService service;

    public AdminVoteRestController(VoteService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll votes");
        return super.getAll();
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id) {
        int userId = service.get(id).getUser().getId();
        return super.get(id, userId);
    }
}