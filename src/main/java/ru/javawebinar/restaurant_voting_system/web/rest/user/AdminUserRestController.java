package ru.javawebinar.restaurant_voting_system.web.rest.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.restaurant_voting_system.to.UserTo;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserTo;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.validateBindingResult;

@RestController
@RequestMapping(value = AdminUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController extends AbstractUserController {

    public static final String REST_URL = "/rest/admin/users";

    @Override
    @ApiOperation(value = "Get all profiles", notes = "Returns a list of profiles.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<UserTo> getAll() {
        return super.getAll();
    }

    @Override
    @ApiOperation(value = "Get profile by id", notes = "Returns a profile as per the id.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public UserTo get(@PathVariable @ApiParam(name = "id", value = "User id", example = "100000") int id) {
        return super.get(id);
    }

    @ApiOperation(value = "Create or update profile with location", notes = "Returns a new or modified profile.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserTo> createOrUpdateWithLocation(@RequestBody @Valid UserTo userTo, BindingResult result) {
        log.info("createOrUpdateWithLocation {}", userTo);
        validateBindingResult(result);
        UserTo modified;

        if (userTo.isNew()) {
            modified = getUserTo(super.create(userTo));
        } else {
            super.update(userTo, userTo.id());
            modified = super.get(userTo.getId());
        }
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + '/' + modified.getId())
                .buildAndExpand(modified.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(modified);
    }

    @Override
    @ApiOperation(value = "Delete profile", notes = "Delete profile by id.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @ApiParam(name = "id", value = "User id", example = "100000") int id) {
        super.delete(id);
    }

    @ApiOperation(value = "Update profile", notes = "Modifies the entire profile.",
            authorizations = {@Authorization(value = "Basic")})
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserTo userTo, @PathVariable @ApiParam(name = "id", value = "User id", example = "100001") int id, BindingResult result) {
        validateBindingResult(result);
        super.update(userTo, id);
    }

    @Override
    @ApiOperation(value = "Get profile by email", notes = "Returns a profile by his email.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/by-email")
    public UserTo getByMail(@RequestParam @ApiParam(name = "email", value = "email", example = "user@yandex.ru") String email) {
        return super.getByMail(email);
    }
}