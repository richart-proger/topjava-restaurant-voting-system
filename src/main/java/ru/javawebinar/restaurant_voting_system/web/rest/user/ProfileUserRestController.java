package ru.javawebinar.restaurant_voting_system.web.rest.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.restaurant_voting_system.AuthorizedUser;
import ru.javawebinar.restaurant_voting_system.to.UserTo;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserTo;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.validateBindingResult;

@RestController
@RequestMapping(value = ProfileUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileUserRestController extends AbstractUserController {

    public static final String REST_URL = "/rest/profile";

    @GetMapping
    public UserTo get(@ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        return super.get(authUser.getId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        super.delete(authUser.getId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserTo userTo, BindingResult result, @ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        validateBindingResult(result);
        super.update(userTo, authUser.getId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserTo> registerWithLocation(@Valid @RequestBody UserTo userTo, BindingResult result) {
        log.info("register {}", userTo);
        validateBindingResult(result);

        UserTo created = getUserTo(super.create(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}