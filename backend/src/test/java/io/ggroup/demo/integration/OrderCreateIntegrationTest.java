package io.ggroup.demo.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.ggroup.demo.model.Customer;
import io.ggroup.demo.model.Seller;

import io.ggroup.demo.repository.OrderRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderCreateIntegrationTest {

  @Autowired
  private TestDataFactory testDataFactory;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Test if customer can create a new order")
    @WithMockUser(username = "customer@test.com", roles = "CUSTOMER")
    void shouldCreateNewOrder() throws Exception {

        long ordersBefore = orderRepository.count();

        Customer customer = testDataFactory.createPersistedCustomer();
        Seller seller = testDataFactory.createPersistedSeller();

        String orderJson = """
                {
                  "customer": {
                    "customerId": %d
                  },
                  "seller": {
                    "sellerId": %d
                  },
                  "date": "2026-01-10T17:32:24.719",
                  "isRefunded": false,
                  "isPaid": true
                }
                """.formatted(customer.getCustomerId(), seller.getSellerId());

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isCreated());

        assertThat(orderRepository.count())
          .isEqualTo(ordersBefore+1);
    }
}