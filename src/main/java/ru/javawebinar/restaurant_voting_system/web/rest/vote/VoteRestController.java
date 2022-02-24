package ru.javawebinar.restaurant_voting_system.web.rest.vote;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @ApiOperation(value = "Get all votes history", notes = "Returns a list of all votes. Allowed for admin.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/history")
    public List<Vote> getAllHistory() {
        log.info("getAll votes");
        return voteService.getAll();
    }

    @ApiOperation(value = "Get vote by id",
            notes = "Returns a vote as per the id from the history of votes. Allowed for admin.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/history/{id}")
    public Vote get(@PathVariable @ApiParam(name = "id", value = "Vote id", example = "100026") int id) {
        log.info("get vote by id={}", id);
        return voteService.get(id);
    }

    /**
     * ------------------------------ PROFILE ------------------------------
     **/

    @ApiOperation(value = "Get user vote by id",
            notes = "Returns a vote as per the id according profile. Allowed with authorization (Profile).",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public VoteTo get(@PathVariable @ApiParam(name = "id", value = "Vote id", example = "100030") int id, @ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("get vote by id={} for authUser={}", id, authUser);
        return getVoteTo(voteService.get(id, authUser.getId()));
    }

    @ApiOperation(value = "Get all user votes",
            notes = "Returns the list of votes of the authorized user. Allowed with authorization (Profile).",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<VoteTo> getAllWithAuthUser(@ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("getAllWithAuthUser authUser={}", authUser);
        return getVoteTos(voteService.getAllWithAuthUser(authUser.getId()));
    }

    @ApiOperation(value = "Delete user vote",
            notes = "Delete user vote by id. Allowed with authorization (Profile).",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @ApiParam(name = "id", value = "Vote id", example = "100026") int id, @ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        log.info("delete vote with id={}", id);
        voteService.delete(id, authUser.getId());
    }
}