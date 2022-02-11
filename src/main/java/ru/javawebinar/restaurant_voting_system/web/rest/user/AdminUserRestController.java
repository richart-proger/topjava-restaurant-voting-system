package ru.javawebinar.restaurant_voting_system.web.rest.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.restaurant_voting_system.model.User;
import ru.javawebinar.restaurant_voting_system.to.UserTo;

import java.net.URI;
import java.util.List;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserFromUserTo;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserTo;

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
    public ResponseEntity<UserTo> createWithLocation(@RequestBody UserTo userTo) {
        log.info("create {}", userTo);
        User newUser = getUserFromUserTo(userTo);
        User created = super.create(newUser);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(getUserTo(created));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @PathVariable int id) {
        super.update(userTo, id);
    }

    @Override
    @GetMapping("/by-email")
    public UserTo getByMail(@RequestParam String email) {
        return super.getByMail(email);
    }
}