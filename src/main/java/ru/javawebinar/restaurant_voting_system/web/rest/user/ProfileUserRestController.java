package ru.javawebinar.restaurant_voting_system.web.rest.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.restaurant_voting_system.to.UserTo;

import static ru.javawebinar.restaurant_voting_system.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = ProfileUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileUserRestController extends AbstractUserController {

    public static final String REST_URL = "/rest/profile";

    @GetMapping
    public UserTo get() {
        return super.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo) {
        super.update(userTo, authUserId());
    }
}