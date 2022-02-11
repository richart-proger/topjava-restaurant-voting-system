package ru.javawebinar.restaurant_voting_system.web.rest.vote;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.restaurant_voting_system.model.Vote;
import ru.javawebinar.restaurant_voting_system.web.rest.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.restaurant_voting_system.data.VoteTestData.*;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getVoteTo;

class AdminVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminVoteRestController.REST_URL + '/';

    @Test
    void getAll() throws Exception {
        Vote v1 = setUserAndRestaurantToNull(VOTE_1);
        Vote v2 = setUserAndRestaurantToNull(VOTE_2);
        Vote v3 = setUserAndRestaurantToNull(VOTE_3);
        Vote v4 = setUserAndRestaurantToNull(VOTE_4);
        Vote v5 = setUserAndRestaurantToNull(VOTE_5);
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(List.of(
                        v5, v4, v3, v2, v1
                )));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(getVoteTo(VOTE_1)));
    }
}