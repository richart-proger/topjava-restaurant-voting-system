package ru.javawebinar.restaurant_voting_system.web.rest.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.restaurant_voting_system.util.ToUtil.*;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.restaurant_voting_system.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private DishService dishService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private UserService userService;

    /**
     * --------------- RESTAURANT ---------------
     **/

    public List<RestaurantTo> getAll() {
        log.info("getAll restaurants");
        return getRestaurantTos(restaurantService.getAll());
    }

    public RestaurantTo get(int id) {
        log.info("get restaurant by id={}", id);
        return getRestaurantTo(restaurantService.get(id));
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        return restaurantService.create(restaurant);
    }

    public void delete(int id) {
        log.info("delete restaurant with id={}", id);
        restaurantService.delete(id);
    }

    public RestaurantTo update(RestaurantTo restaurantTo, int id) {
        log.info("update restaurant {} with id={}", restaurantTo, id);
        Restaurant restaurant = getRestaurantFromRestaurantTo(restaurantTo);
        assureIdConsistent(restaurant, id);
        return getRestaurantTo(restaurantService.update(restaurant));
    }

    /**
     * --------------- MENU ---------------
     **/

    public Map<LocalDate, List<MenuTo>> getAllWithMenu(LocalDate startDate, LocalDate endDate) {
        log.info("getAll restaurants with menus between startDate={} and endDate={}", startDate, endDate);
        List<Restaurant> restaurants = restaurantService.getAll();
        List<Dish> dishes = dishService.getAll();

        List<MenuTo> menuTos = getFilteredMenuTosByRestaurantList(restaurants, dishes, startDate, endDate);
        return getMenuTosFilteredByDate(menuTos);
    }

    public Map<LocalDate, List<MenuTo>> getWithMenu(int id, LocalDate startDate, LocalDate endDate) {
        log.info("getWithMenu by restaurantId={} between startDate={} and endDate={}", id, startDate, endDate);
        Restaurant restaurant = restaurantService.get(id);
        List<Dish> dishes = dishService.getAllDishByRestaurantId(id);

        List<MenuTo> menuTos = getFilteredMenuTosByRestaurant(restaurant, dishes, startDate, endDate);
        return getMenuTosFilteredByDate(menuTos);
    }

    public List<Dish> createMenu(List<Dish> menu) {
        log.info("createMenu {}", menu);
        menu.forEach(ValidationUtil::checkNew);
        return dishService.createMenu(menu);
    }

    public void deleteMenu(int restaurantId) {
        log.info("deleteMenu with restaurantId={}", restaurantId);
        dishService.deleteMenu(restaurantId, LocalDate.now());
    }

    public List<Dish> updateMenu(List<Dish> menu, int restaurantId) {
        log.info("updateMenu {} with restaurantId={}", menu, restaurantId);
        menu.forEach(dish -> assureIdConsistent(dish.getRestaurant(), restaurantId));
        return dishService.createMenu(menu);
    }

    /**
     * --------------- VOTE ---------------
     **/

    public VoteTo voteForRestaurant(int restaurantId, int authUserId) {
        log.info("voteForRestaurant with id={} by userId={}", restaurantId, authUserId);
        User user = userService.get(authUserId);
        Restaurant restaurant = restaurantService.get(restaurantId);
        Vote newVote = new Vote(null, user, restaurant, LocalDate.now());
        Vote voteToUpdate = voteService.getForToday(authUserId);

        if (voteToUpdate != null) {
            newVote.setId(voteToUpdate.getId());
            return getVoteTo(voteService.update(newVote, authUserId, LocalTime.now()));
        }
        return getVoteTo(voteService.create(newVote, authUserId));
    }
}