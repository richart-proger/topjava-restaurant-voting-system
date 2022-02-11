package ru.javawebinar.restaurant_voting_system.web.rest.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.restaurant_voting_system.service.UserService;
import ru.javawebinar.restaurant_voting_system.to.UserTo;
import ru.javawebinar.restaurant_voting_system.web.rest.AbstractControllerTest;
import ru.javawebinar.restaurant_voting_system.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.*;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserTo;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getUserTos;
import static ru.javawebinar.restaurant_voting_system.web.rest.user.ProfileUserRestController.REST_URL;

class ProfileUserRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_TO_MATCHER.contentJson(getUserTo(USER)));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        USER_TO_MATCHER.assertMatch(getUserTos(userService.getAll()), getUserTo(ADMIN));
    }

    @Test
    void update() throws Exception {
        UserTo updated = getUserTo(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_TO_MATCHER.assertMatch(getUserTo(userService.get(USER_ID)), updated);
    }
}