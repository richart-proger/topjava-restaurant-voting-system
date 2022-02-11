package ru.javawebinar.restaurant_voting_system.web.rest.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.model.Restaurant;
import ru.javawebinar.restaurant_voting_system.service.DishService;
import ru.javawebinar.restaurant_voting_system.service.RestaurantService;
import ru.javawebinar.restaurant_voting_system.to.MenuTo;
import ru.javawebinar.restaurant_voting_system.to.RestaurantTo;
import ru.javawebinar.restaurant_voting_system.util.exception.ForbiddenException;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.*;

@RestController
@RequestMapping(value = AdminRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController extends AbstractRestaurantController {

    public static final String REST_URL = "/rest/admin/restaurants";

    private final RestaurantService restaurantService;
    private final DishService dishService;

    public AdminRestaurantRestController(RestaurantService restaurantService, DishService dishService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
    }

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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> createWithLocation(@RequestBody RestaurantTo restaurantTo) {
        log.info("createWithLocation restaurant {}", restaurantTo);
        Restaurant newRestaurant = getRestaurantFromRestaurantTo(restaurantTo);
        Restaurant created = super.create(newRestaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(getRestaurantTo(created));
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTo> updateWithLocation(@RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("updateWithLocation restaurant {}", restaurantTo);
        return ResponseEntity.ok(super.update(restaurantTo, id));
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

    @PostMapping(value = "/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<LocalDate, List<MenuTo>>> createMenuWithLocation(@PathVariable(name = "id") int restaurantId, @RequestBody MenuTo menuTo) {
        log.info("createMenuWithLocation for restaurant with id={} and menu={}", restaurantId, menuTo);

        if (dishService.getDishByDate(restaurantId, LocalDate.now()).isEmpty()) {
            Restaurant restaurant = restaurantService.get(restaurantId);
            List<Dish> newMenu = getDishesFromTos(menuTo.getMenu());
            setRestaurantAndDateInMenu(newMenu, restaurant);
            List<Dish> resultMenu = super.createMenu(newMenu);
            List<MenuTo> created = getFilteredMenuTosByRestaurant(restaurant, resultMenu, null, null);
            return ResponseEntity.ok(getMenuTosFilteredByDate(created));
        } else {
            throw new ForbiddenException("Menu for today already exists. You can update it or delete it");
        }
    }

    @Override
    @DeleteMapping("/{id}/menu")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable(name = "id") int restaurantId) {
        if (dishService.getDishByDate(restaurantId, LocalDate.now()).isEmpty()) {
            throw new ForbiddenException("There is nothing to delete, there is no menu for today");
        }
        super.deleteMenu(restaurantId);
    }

    @PutMapping(value = "/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<LocalDate, List<MenuTo>>> updateMenuWithLocation(@PathVariable(name = "id") int restaurantId, @RequestBody MenuTo menuTo) {
        log.info("updateMenuWithLocation for restaurant with id={} and menu={}", restaurantId, menuTo);

        if (dishService.getDishByDate(restaurantId, LocalDate.now()).isEmpty()) {
            throw new ForbiddenException("There is nothing to update, there is no menu for today");
        }
        Restaurant restaurant = restaurantService.get(restaurantId);
        deleteMenu(restaurantId);
        List<Dish> menuForUpdate = getDishesFromTos(menuTo.getMenu());
        setRestaurantAndDateInMenu(menuForUpdate, restaurant);
        List<Dish> updatedMenu = super.updateMenu(menuForUpdate, restaurantId);
        List<MenuTo> menuTos = getFilteredMenuTosByRestaurant(restaurant, updatedMenu, null, null);
        return ResponseEntity.ok(getMenuTosFilteredByDate(menuTos));
    }
}