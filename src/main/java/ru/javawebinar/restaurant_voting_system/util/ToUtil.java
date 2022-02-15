package ru.javawebinar.restaurant_voting_system.util;

import ru.javawebinar.restaurant_voting_system.model.*;
import ru.javawebinar.restaurant_voting_system.to.*;

import java.time.LocalDate;
import java.util.*;

import static ru.javawebinar.restaurant_voting_system.util.DateUtil.getDateList;

public class ToUtil {

    /**
     * -------------------- UserTo --------------------
     **/
    public static List<UserTo> getUserTos(List<User> userList) {
        return userList.stream()
                .map(ToUtil::getUserTo)
                .toList();
    }

    public static UserTo getUserTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }

    public static User createNewFromTo(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    /**
     * -------------------- VoteTo --------------------
     **/
    public static List<VoteTo> getVoteTos(List<Vote> voteList) {
        return voteList.stream()
                .map(ToUtil::getVoteTo)
                .toList();
    }

    public static VoteTo getVoteTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getRestaurantName(), vote.getBookingDate());
    }

    /**
     * -------------------- RestaurantTo --------------------
     **/
    public static List<RestaurantTo> getRestaurantTos(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(ToUtil::getRestaurantTo)
                .toList();
    }

    public static Map<LocalDate, List<MenuTo>> getMenuTosFilteredByDate(List<MenuTo> menuTos) {
        Map<LocalDate, List<MenuTo>> map = new TreeMap<>();
        for (MenuTo m : menuTos) {
            if (m != null) {
                if (map.containsKey(m.getMenuDate())) {
                    map.get(m.getMenuDate()).add(m);
                } else {
                    map.put(m.getMenuDate(), new ArrayList<>(List.of(m)));
                }
            }
        }
        return new TreeMap<>(map).descendingMap();
    }

    public static RestaurantTo getRestaurantTo(Restaurant restaurant) {
        return new RestaurantTo(
                restaurant.getId(),
                restaurant.getName()
        );
    }

    public static Restaurant getRestaurantFromRestaurantTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getRestaurantName());
    }

    /**
     * -------------------- MenuTo --------------------
     **/
    public static List<MenuTo> getFilteredMenuTosByRestaurant(Restaurant restaurant, List<Dish> dishList, LocalDate starDate, LocalDate endDate) {
        List<LocalDate> dateList = getDateList(starDate, endDate);
        return dateList.stream()
                .map(date -> getFilteredMenuTo(restaurant, dishList, date))
                .filter(Objects::nonNull)
                .toList();
    }

    public static List<MenuTo> getFilteredMenuTosByRestaurantList(List<Restaurant> restaurants, List<Dish> dishList, LocalDate starDate, LocalDate endDate) {
        List<LocalDate> dateList = getDateList(starDate, endDate);

        return restaurants.stream()
                .flatMap(restaurant ->
                        dateList.stream()
                                .map(date -> getFilteredMenuTo(restaurant, dishList, date))
                                .filter(Objects::nonNull))
                .toList();
    }

    public static MenuTo getFilteredMenuTo(Restaurant restaurant, List<Dish> dishList, LocalDate date) {
        List<Dish> dishes = dishList.stream()
                .filter(dish -> dish.getDate().equals(date))
                .filter(dish -> dish.getRestaurant().getId().equals(restaurant.getId()))
                .toList();
        if (dishes.isEmpty())
            return null;
        return new MenuTo(
                restaurant.getId(),
                restaurant.getName(),
                getFilteredDishTos(dishes, date),
                date
        );
    }

    /**
     * -------------------- DishTo --------------------
     **/
    public static List<DishTo> getFilteredDishTos(List<Dish> dishList, LocalDate date) {
        List<Dish> filteredDishes = dishList.stream()
                .filter(dish -> dish.getDate().equals(date))
                .toList();
        return getDishTos(filteredDishes);
    }

    public static List<DishTo> getDishTos(List<Dish> dishList) {
        return dishList.stream()
                .map(ToUtil::getDishTo)
                .toList();
    }

    public static List<Dish> getDishesFromTos(List<DishTo> dishTos) {
        return dishTos.stream()
                .map(ToUtil::getDishFromDishTo)
                .toList();
    }

    public static DishTo getDishTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice());
    }

    public static Dish getDishFromDishTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getDishName(), dishTo.getPrice());
    }

    public static void setRestaurantAndDateInMenu(List<Dish> menu, Restaurant restaurant) {
        menu.forEach(dish -> {
            dish.setRestaurant(restaurant);
            dish.setRestaurantName();
            dish.setDate(LocalDate.now());
        });
    }
}