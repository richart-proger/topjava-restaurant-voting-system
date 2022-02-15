package ru.javawebinar.restaurant_voting_system.web.rest.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.restaurant_voting_system.service.VoteService;
import ru.javawebinar.restaurant_voting_system.util.exception.NotFoundException;
import ru.javawebinar.restaurant_voting_system.web.rest.AbstractControllerTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.restaurant_voting_system.TestUtil.userHttpBasic;
import static ru.javawebinar.restaurant_voting_system.data.UserTestData.USER;
import static ru.javawebinar.restaurant_voting_system.data.VoteTestData.*;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getVoteTo;
import static ru.javawebinar.restaurant_voting_system.util.ToUtil.getVoteTos;

class ProfileVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileVoteRestController.REST_URL + '/';

    @Autowired
    private VoteService voteService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_ID)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(getVoteTo(VOTE_1)));
    }

    @Test
    void getAllWithAuthUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(getVoteTos(List.of(
                        voteService.get(VOTE_5.getId()),
                        voteService.get(VOTE_2.getId()),
                        voteService.get(VOTE_1.getId())
                ))));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> getVoteTo(voteService.get(VOTE_ID)));
    }
}