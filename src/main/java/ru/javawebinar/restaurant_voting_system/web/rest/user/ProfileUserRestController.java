package ru.javawebinar.restaurant_voting_system.web.rest.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.restaurant_voting_system.AuthorizedUser;
import ru.javawebinar.restaurant_voting_system.to.UserTo;

import javax.validation.Valid;

import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.validateBindingResult;

@RestController
@RequestMapping(value = ProfileUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileUserRestController extends AbstractUserController {

    public static final String REST_URL = "/rest/profile";

    @GetMapping
    public UserTo get(@AuthenticationPrincipal AuthorizedUser authUser) {
        return super.get(authUser.getId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authUser) {
        super.delete(authUser.getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserTo userTo, BindingResult result, @AuthenticationPrincipal AuthorizedUser authUser) {
        validateBindingResult(result);
        super.update(userTo, authUser.getId());
    }
}