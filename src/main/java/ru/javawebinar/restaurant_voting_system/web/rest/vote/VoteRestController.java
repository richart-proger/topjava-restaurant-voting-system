package ru.javawebinar.restaurant_voting_system.web.rest.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.restaurant_voting_system.AuthorizedUser;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.service.VoteService;
import ru.javawebinar.restaurant_voting_system.to.VoteTo;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getVoteTo;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getVoteTos;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    public static final String REST_URL = "/rest/votes";
    protected final Logger log = LoggerFactory.getLogger(VoteRestController.class);

    private final VoteService voteService;

    public VoteRestController(VoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * ------------------------------ ADMIN ------------------------------
     **/

    @GetMapping("/history")
    public List<Vote> getAllHistory() {
        log.info("getAll votes");
        return voteService.getAll();
    }

    @GetMapping("/history/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote by id={}", id);
        int userId = voteService.get(id).getUser().getId();
        return voteService.get(id, userId);
    }

    /**
     * ------------------------------ PROFILE ------------------------------
     **/

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id, @ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get vote by id={} for authUser={}", id, authUser);
        return getVoteTo(voteService.get(id, authUser.getId()));
    }

    @GetMapping
    public List<VoteTo> getAllWithAuthUser(@ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("getAllWithAuthUser authUser={}", authUser);
        return getVoteTos(voteService.getAllWithAuthUser(authUser.getId()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("delete vote with id={}", id);
        voteService.delete(id, authUser.getId());
    }
}