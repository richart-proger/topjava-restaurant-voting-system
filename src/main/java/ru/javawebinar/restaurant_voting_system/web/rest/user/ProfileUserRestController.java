package ru.javawebinar.restaurant_voting_system.web.rest.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @ApiOperation(value = "Get profile",
            notes = "Returns a user profile. Allowed with authorization (Profile).",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public UserTo get(@ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        return super.get(authUser.getId());
    }

    @ApiOperation(value = "Delete profile",
            notes = "Delete a user profile. Allowed with authorization (Profile).",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        super.delete(authUser.getId());
    }

    @ApiOperation(value = "Update profile",
            notes = "Modifies the entire profile. Allowed with authorization (Profile).",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserTo userTo, BindingResult result, @ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser) {
        validateBindingResult(result);
        super.update(userTo, authUser.getId());
    }

    @ApiOperation(value = "Register user",
            notes = "Register a new user profile. Allowed for unregistered users (Anonymous).")
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