package ru.javawebinar.restaurant_voting_system.web.rest.user;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.restaurant_voting_system.to.UserTo;

import javax.validation.Valid;
import java.net.URI;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserTo;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.validateBindingResult;

@RestController
@RequestMapping(value = UnauthorisedUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UnauthorisedUserRestController extends AbstractUserController {

    public static final String REST_URL = "/rest";

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTo> register(@Valid @RequestBody UserTo userTo, BindingResult result) {
        log.info("register {}", userTo);
        validateBindingResult(result);

        UserTo created = getUserTo(super.create(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/profile")
                .buildAndExpand().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}