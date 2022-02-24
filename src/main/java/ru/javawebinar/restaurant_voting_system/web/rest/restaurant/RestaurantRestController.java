package ru.javawebinar.restaurant_voting_system.web.rest.restaurant;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.restaurant_voting_system.AuthorizedUser;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;
import ru.javawebinar.restaurant_voting_system.model.User;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.service.DishService;
import ru.javawebinar.restaurant_voting_system.service.RestaurantService;
import ru.javawebinar.restaurant_voting_system.service.UserService;
import ru.javawebinar.restaurant_voting_system.service.VoteService;
import ru.javawebinar.restaurant_voting_system.to.MenuTo;
import ru.javawebinar.restaurant_voting_system.to.RestaurantTo;
import ru.javawebinar.restaurant_voting_system.to.VoteTo;
import ru.javawebinar.restaurant_voting_system.util.ValidationUtil;
import ru.javawebinar.restaurant_voting_system.util.exception.ForbiddenException;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.*;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {

    public static final String REST_URL = "/rest/restaurants";
    protected final Logger log = LoggerFactory.getLogger(RestaurantRestController.class);

    private final RestaurantService restaurantService;
    private final DishService dishService;
    private final UserService userService;
    private final VoteService voteService;

    public RestaurantRestController(
            RestaurantService restaurantService, DishService dishService,
            UserService userService, VoteService voteService
    ) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
        this.userService = userService;
        this.voteService = voteService;
    }

    /**
     * ------------------------------ RESTAURANT ------------------------------
     **/

    @ApiOperation(value = "Get all restaurants",
            notes = "Returns a list of restaurants. Allowed with or without authorization (Unauthorized/Profile/Admin).")
    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll restaurants");
        return getRestaurantTos(restaurantService.getAll());
    }

    @ApiOperation(value = "Get restaurant by id",
            notes = "Returns a restaurant as per the id. Allowed with authorization (Profile/Admin).",
            authorizations = {@Authorization(value = "Basic")})
    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable @ApiParam(name = "id", value = "Restaurant id", example = "100002") int id) {
        log.info("get restaurant by id={}", id);
        return getRestaurantTo(restaurantService.get(id));
    }

    @ApiOperation(value = "Create restaurant with location",
            notes = "Returns a new restaurant. Allowed for admin.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestaurantTo> createWithLocation(@RequestBody @Valid RestaurantTo restaurantTo, BindingResult result) {
        log.info("createWithLocation restaurant {}", restaurantTo);
        validateBindingResult(result);

        checkNew(restaurantTo);
        Restaurant created = restaurantService.create(createNewFromRestaurantTo(restaurantTo));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(getRestaurantTo(created));
    }

    @ApiOperation(value = "Delete restaurant", notes = "Delete restaurant by id. Allowed for admin.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @ApiParam(name = "id", value = "Restaurant id", example = "100002") int id) {
        log.info("delete restaurant with id={}", id);
        restaurantService.delete(id);
    }

    @ApiOperation(value = "Update restaurant", notes = "Modifies the entire restaurant. Allowed for admin.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @RequestBody @Valid RestaurantTo restaurantTo,
            @PathVariable @ApiParam(name = "id", value = "Restaurant id", example = "100005") int id,
            BindingResult result
    ) {
        log.info("update restaurant {}", restaurantTo);
        validateBindingResult(result);

        assureIdConsistent(restaurantTo, id);
        restaurantService.update(restaurantTo);
    }

    /**
     * ------------------------------ MENU ------------------------------
     **/

    @ApiOperation(value = "Get all restaurants with menus",
            notes = "Returns a list of restaurants with menus between startDate and endDate. " +
                    "If the startDate and endDate are not specified, the menu for today will be displayed. " +
                    "Allowed with or without authorization (Unauthorized/Profile/Admin).")
    @GetMapping("/menus")
    public Map<LocalDate, List<MenuTo>> getAllWithMenu(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        log.info("getAll restaurants with menus between startDate={} and endDate={}", startDate, endDate);
        List<Restaurant> restaurants = restaurantService.getAll();
        List<Dish> dishes = dishService.getAll();

        List<MenuTo> menuTos = getFilteredMenuTosByRestaurantList(restaurants, dishes, startDate, endDate);
        return getMenuTosFilteredByDate(menuTos);
    }

    @ApiOperation(value = "Get a specific restaurant with its menu/menus",
            notes = "Returns a restaurant with menus between startDate and endDate. " +
                    "If the startDate and endDate are not specified, the menu for today will be displayed. " +
                    "Allowed with authorization (Profile/Admin).",
            authorizations = {@Authorization(value = "Basic")})
    @GetMapping("/{id}/menu")
    public Map<LocalDate, List<MenuTo>> getWithMenu(
            @PathVariable @ApiParam(name = "id", value = "Restaurant id", example = "100002") int id,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        log.info("getWithMenu by restaurantId={} between startDate={} and endDate={}", id, startDate, endDate);
        Restaurant restaurant = restaurantService.get(id);
        List<Dish> dishes = dishService.getAllDishByRestaurantId(id);

        List<MenuTo> menuTos = getFilteredMenuTosByRestaurant(restaurant, dishes, startDate, endDate);
        return getMenuTosFilteredByDate(menuTos);
    }

    @ApiOperation(value = "Create menu with location",
            notes = "Returns a new menu for today for a specific restaurant. Allowed for admin.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<LocalDate, List<MenuTo>>> createMenuWithLocation(
            @PathVariable(name = "id") @ApiParam(name = "id", value = "Restaurant id", example = "100005") int restaurantId,
            @RequestBody @Valid MenuTo menuTo, BindingResult result
    ) {
        log.info("createMenuWithLocation for restaurant with id={} and menu={}", restaurantId, menuTo);
        validateBindingResult(result);

        if (dishService.getDishByDate(restaurantId, LocalDate.now()).isEmpty()) {
            Restaurant restaurant = restaurantService.get(restaurantId);
            List<Dish> newMenu = getDishesFromTos(menuTo.getMenu());
            setRestaurantAndDateInMenu(newMenu, restaurant);
            newMenu.forEach(ValidationUtil::checkNew);
            dishService.createMenu(newMenu);

            List<MenuTo> created = getFilteredMenuTosByRestaurant(restaurant, newMenu, null, null);

            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}/menu")
                    .buildAndExpand(restaurantId).toUri();
            return ResponseEntity.created(uriOfNewResource).body(getMenuTosFilteredByDate(created));
        } else {
            throw new ForbiddenException("Menu for today already exists. You can update it or delete it");
        }
    }

    @ApiOperation(value = "Delete menu", notes = "Deletes the menu if it exists for specific restaurant. Allowed for admin.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/menu")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(
            @PathVariable(name = "id") @ApiParam(name = "id", value = "Restaurant id", example = "100002") int restaurantId
    ) {
        log.info("deleteMenu with restaurantId={}", restaurantId);
        if (dishService.getDishByDate(restaurantId, LocalDate.now()).isEmpty()) {
            throw new ForbiddenException("There is nothing to delete, there is no menu for today");
        }
        dishService.deleteMenu(restaurantId, LocalDate.now());
    }

    @ApiOperation(value = "Update menu", notes = "Modifies the entire menu if it exists for specific restaurant. Allowed for admin.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(
            @PathVariable(name = "id") @ApiParam(name = "id", value = "Restaurant id", example = "100005") int restaurantId,
            @RequestBody @Valid MenuTo menuTo, BindingResult result
    ) {
        log.info("updateMenu for restaurant with id={} and menu={}", restaurantId, menuTo);
        validateBindingResult(result);

        if (dishService.getDishByDate(restaurantId, LocalDate.now()).isEmpty()) {
            throw new ForbiddenException("There is nothing to update, there is no menu for today");
        }
        Restaurant restaurant = restaurantService.get(restaurantId);
        deleteMenu(restaurantId);
        List<Dish> menuForUpdate = getDishesFromTos(menuTo.getMenu());
        setRestaurantAndDateInMenu(menuForUpdate, restaurant);
        menuForUpdate.forEach(dish -> assureIdConsistent(dish.getRestaurant(), restaurantId));
        dishService.createMenu(menuForUpdate);
    }

    /**
     * ------------------------------ VOTE ------------------------------
     **/

    @ApiOperation(value = "Create vote with location",
            notes = "Returns a new vote for specific restaurant. Allowed with authorization (Profile/Admin).",
            authorizations = {@Authorization(value = "Basic")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - Successfully retrieved"),
            @ApiResponse(code = 401, message = "Unauthorized - The request requires user authentication"),
            @ApiResponse(code = 403, message = "Forbidden - The server understood the request, but is refusing to authorize it"),
            @ApiResponse(code = 404, message = "Not found - The restaurant was not found"),
            @ApiResponse(code = 405, message = "Method Not Allowed - Voting time is expired")
    })
    @PostMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VoteTo> voteForRestaurantWithLocation(
            @PathVariable(name = "id") @ApiParam(name = "id", value = "Restaurant id",
                    example = "100003") int restaurantId, @ApiIgnore @AuthenticationPrincipal AuthorizedUser authUser
    ) {
        log.info("voteForRestaurant with id={} by userId={}", restaurantId, authUser.getId());
        VoteTo created;
        User user = userService.get(authUser.getId());
        Restaurant restaurant = restaurantService.get(restaurantId);
        Vote newVote = getNewVoteTo(user, restaurant);
        Vote voteToUpdate = voteService.getForToday(authUser.getId());

        if (voteToUpdate != null) {
            newVote.setId(voteToUpdate.getId());
            created = getVoteTo(voteService.update(newVote, authUser.getId(), LocalTime.now()));
        } else {
            created = getVoteTo(voteService.create(newVote, authUser.getId()));
        }
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}