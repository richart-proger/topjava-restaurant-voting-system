package ru.javawebinar.restaurant_voting_system.web.rest.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.restaurant_voting_system.web.rest.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.restaurant_voting_system.TestUtil.userHttpBasic;
import static ru.javawebinar.restaurant_voting_system.data.RestaurantTestData.*;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.USER;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getRestaurantTo;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getRestaurantTos;

class ProfileRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestaurantRestController.REST_URL + '/';

    /**
     * ------------------------------ RESTAURANT ------------------------------
     **/

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getRestaurantTos(getAllSorted())));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(getRestaurantTo(RESTAURANT_1)));
    }
}