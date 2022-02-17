package ru.javawebinar.restaurant_voting_system.web.rest.restaurant;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.restaurant_voting_system.AuthorizedUser;
import ru.javawebinar.restaurant_voting_system.to.MenuTo;
import ru.javawebinar.restaurant_voting_system.to.RestaurantTo;
import ru.javawebinar.restaurant_voting_system.to.VoteTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = ProfileRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestaurantRestController extends AbstractRestaurantController {

    public static final String REST_URL = "/rest/profile/restaurants";

    /**
     * ------------------------------ RESTAURANT ------------------------------
     **/

    @Override
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        return super.get(id);
    }

    /**
     * ------------------------------ MENU ------------------------------
     **/

    @Override
    @GetMapping("/menus")
    public Map<LocalDate, List<MenuTo>> getAllWithMenu(
            @RequestParam @Nullable LocalDate startDate,
            @RequestParam @Nullable LocalDate endDate
    ) {
        return super.getAllWithMenu(startDate, endDate);
    }

    @Override
    @GetMapping("/{id}/with-menu")
    public Map<LocalDate, List<MenuTo>> getWithMenu(
            @PathVariable int id,
            @RequestParam @Nullable LocalDate startDate,
            @RequestParam @Nullable LocalDate endDate
    ) {
        return super.getWithMenu(id, startDate, endDate);
    }

    /**
     * ------------------------------ VOTE ------------------------------
     **/

    @PostMapping(value = "/{id}")
    public ResponseEntity<VoteTo> voteForRestaurant(@PathVariable(name = "id") int restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        VoteTo created = super.voteForRestaurant(restaurantId, authUser.getId());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}