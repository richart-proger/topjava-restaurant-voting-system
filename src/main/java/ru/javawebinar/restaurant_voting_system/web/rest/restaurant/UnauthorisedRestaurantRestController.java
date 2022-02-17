package ru.javawebinar.restaurant_voting_system.web.rest.restaurant;

import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.javawebinar.restaurant_voting_system.to.MenuTo;
import ru.javawebinar.restaurant_voting_system.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = UnauthorisedRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UnauthorisedRestaurantRestController extends AbstractRestaurantController {

    public static final String REST_URL = "/rest/restaurants";

    /**
     * ------------------------------ RESTAURANT ------------------------------
     **/

    @Override
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
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
}