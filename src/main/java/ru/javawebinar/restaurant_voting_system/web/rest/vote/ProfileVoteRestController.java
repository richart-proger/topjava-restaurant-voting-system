package ru.javawebinar.restaurant_voting_system.web.rest.vote;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.restaurant_voting_system.AuthorizedUser;
import ru.javawebinar.restaurant_voting_system.to.VoteTo;

import java.util.List;

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteRestController extends AbstractVoteRestController {

    public static final String REST_URL = "/rest/profile/votes";

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        return super.get(id, authUser.getId());
    }

    @GetMapping
    public List<VoteTo> getAllWithAuthUser(@AuthenticationPrincipal AuthorizedUser authUser) {
        return super.getAllWithAuthUser(authUser.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthorizedUser authUser) {
        super.delete(id, authUser.getId());
    }
}