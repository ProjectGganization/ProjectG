package io.ggroup.demo.integration;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.ggroup.demo.model.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EventSearchIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataFactory testDataFactory;

    @Test
    @DisplayName("Customer can search events")
    @WithMockUser(roles = "CUSTOMER")
    void customerCanSearchEvents() throws Exception {

        Event matchingEvent = testDataFactory.createPersistedEvent("Random Test Event");
        Event otherEvent = testDataFactory.createPersistedEvent("Completely Different Show");

        String query = "Random";

        mockMvc.perform(get("/api/events")
                .param("query", query))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(matchingEvent.getTitle())))
                .andExpect(content().string(not(containsString(otherEvent.getTitle()))));
    }
}