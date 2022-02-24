package ru.javawebinar.restaurant_voting_system.web.rest.dish;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {

    private static final Logger log = getLogger(DishRestController.class);

    public static final String REST_URL = "/rest/admin/dishes";

    private final DishService dishService;
    private final RestaurantService restaurantService;

    public DishRestController(DishService dishService, RestaurantService restaurantService) {
        this.dishService = dishService;
        this.restaurantService = restaurantService;
    }

    @ApiOperation(value = "Get all dishes", notes = "Returns a list of dishes.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<Dish> getAll() {
        log.info("getAll dishes");
        return dishService.getAll();
    }

    @ApiOperation(value = "Get dish by id", notes = "Returns a dish as per the id.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public DishTo get(@PathVariable @ApiParam(name = "id", value = "Dish id", example = "100010") int id) {
        log.info("get dish by id={}", id);
        return getDishTo(dishService.get(id));
    }

    @ApiOperation(value = "Create dish with location",
            notes = "Returns a new dish to restaurant by id. The method allows you to add one dish to today's menu.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DishTo> createDishWithLocation(@PathVariable(name = "id") @ApiParam(name = "id", value = "Restaurant id", example = "100005") int restaurantId, @RequestBody @Valid DishTo dishTo, BindingResult result) {
        log.info("createWithLocation for restaurant with id={} and dish={}", restaurantId, dishTo);
        validateBindingResult(result);

        Dish newDish = createNewFromDishTo(dishTo, restaurantService.get(restaurantId));
        Dish created = dishService.create(newDish);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(getDishTo(created));
    }

    @ApiOperation(value = "Delete dish", notes = "Deletes the dish if it exists.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @ApiParam(name = "id", value = "Dish id", example = "100010") int id) {
        log.info("delete dish with id={}", id);
        if (dishService.get(id) == null) {
            throw new ForbiddenException("There is nothing to delete");
        }
        dishService.delete(id);
    }

    @ApiOperation(value = "Update dish", notes = "Modifies the entire dish if it exists.",
            authorizations = {@Authorization(value = "Basic")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid DishTo dishTo, @PathVariable @ApiParam(name = "id", value = "Dish id", example = "100010") int id, BindingResult result) {
        log.info("update dish {} with id={}", dishTo, id);
        validateBindingResult(result);

        if (dishService.get(id) == null) {
            throw new ForbiddenException("There is nothing to update");
        }
        Dish dish = updateDishFromTo(dishService.get(id), dishTo);
        assureIdConsistent(dishTo, id);
        dishService.update(dish);
    }
}