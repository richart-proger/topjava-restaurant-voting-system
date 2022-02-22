package ru.javawebinar.restaurant_voting_system.web.rest.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @GetMapping
    public List<UserTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public UserTo get(@PathVariable int id) {
        return super.get(id);
    }

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
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserTo userTo, @PathVariable int id, BindingResult result) {
        validateBindingResult(result);
        super.update(userTo, id);
    }

    @Override
    @GetMapping("/by-email")
    public UserTo getByMail(@RequestParam String email) {
        return super.getByMail(email);
    }
}