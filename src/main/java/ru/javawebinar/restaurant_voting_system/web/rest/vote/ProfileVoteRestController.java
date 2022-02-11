package ru.javawebinar.restaurant_voting_system.web.rest.vote;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.restaurant_voting_system.to.VoteTo;

import java.util.List;

import static ru.javawebinar.restaurant_voting_system.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = ProfileVoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteRestController extends AbstractVoteRestController {

    public static final String REST_URL = "/rest/profile/votes";

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id) {
        return super.get(id, authUserId());
    }

    @GetMapping
    public List<VoteTo> getAllWithAuthUser() {
        return super.getAllWithAuthUser(authUserId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id, authUserId());
    }
}