package ru.javawebinar.restaurant_voting_system.web.rest.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.restaurant_voting_system.service.RestaurantService;
import ru.javawebinar.restaurant_voting_system.to.RestaurantTo;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;
import ru.javawebinar.restaurant_voting_system.web.AbstractControllerTest;
import ru.javawebinar.restaurant_voting_system.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.restaurant_voting_system.data.RestaurantTestData.*;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getRestaurantTo;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getRestaurantTos;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantService restaurantService;

    /**
     * ------------------------------ RESTAURANT ------------------------------
     **/

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getRestaurantTos(getAllSorted())));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getRestaurantTo(RESTAURANT_1)));
    }

    @Test
    void createWithLocation() throws Exception {
        RestaurantTo newRestaurantTo = getRestaurantTo(getNewRestaurant());

        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurantTo)))
                .andExpect(status().isCreated());

        RestaurantTo created = RESTAURANT_TO_MATCHER.readFromJson(action);
        int newId = created.getRestaurantId();
        newRestaurantTo.setRestaurantId(newId);
        RESTAURANT_TO_MATCHER.assertMatch(created, newRestaurantTo);
        RESTAURANT_TO_MATCHER.assertMatch(getRestaurantTo(restaurantService.get(newId)), newRestaurantTo);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> getRestaurantTo(restaurantService.get(RESTAURANT_ID)));
    }

    @Test
    void updateWithLocation() throws Exception {
        RestaurantTo updated = getRestaurantTo(getUpdatedRestaurant());
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        RESTAURANT_TO_MATCHER.assertMatch(getRestaurantTo(restaurantService.get(RESTAURANT_ID)), updated);
    }
}