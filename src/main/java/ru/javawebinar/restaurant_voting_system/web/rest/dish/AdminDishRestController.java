package ru.javawebinar.restaurant_voting_system.web.rest.dish;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.restaurant_voting_system.model.Dish;
import ru.javawebinar.restaurant_voting_system.service.DishService;
import ru.javawebinar.restaurant_voting_system.service.RestaurantService;
import ru.javawebinar.restaurant_voting_system.to.DishTo;
import ru.javawebinar.restaurant_voting_system.util.exception.ForbiddenException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.*;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.validateBindingResult;

@RestController
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishRestController {

    private static final Logger log = getLogger(AdminDishRestController.class);

    public static final String REST_URL = "/rest/admin/dishes";

    private final DishService dishService;
    private final RestaurantService restaurantService;

    public AdminDishRestController(DishService dishService, RestaurantService restaurantService) {
        this.dishService = dishService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll dishes");
        return dishService.getAll();
    }

    @GetMapping("/{id}")
    public DishTo get(@PathVariable int id) {
        log.info("get dish by id={}", id);
        return getDishTo(dishService.get(id));
    }

    @PostMapping(value = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> createDishWithLocation(@PathVariable(name = "id") int restaurantId, @RequestBody @Valid DishTo dishTo, BindingResult result) {
        log.info("createWithLocation for restaurant with id={} and dish={}", restaurantId, dishTo);
        validateBindingResult(result);

        Dish newDish = getDishFromDishTo(dishTo);
        setRestaurantAndDateInMenu(List.of(newDish), restaurantService.get(restaurantId));
        Dish created = dishService.create(newDish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(getDishTo(created));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id={}", id);
        if (dishService.get(id) == null) {
            throw new ForbiddenException("There is nothing to delete");
        }
        dishService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DishTo> updateWithLocation(@RequestBody @Valid DishTo dishTo, @PathVariable int id, BindingResult result) {
        log.info("update dish {} with id={}", dishTo, id);
        validateBindingResult(result);

        if (dishService.get(id) == null) {
            throw new ForbiddenException("There is nothing to update");
        }
        Dish dish = getDishFromDishTo(dishTo);
        setRestaurantAndDateInMenu(List.of(dish), dishService.get(id).getRestaurant());
        assureIdConsistent(dish, id);
        return ResponseEntity.ok(getDishTo(dishService.update(dish)));
    }
}