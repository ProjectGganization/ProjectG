// package io.ggroup.demo.controller;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import io.ggroup.demo.model.Order;
// import io.ggroup.demo.repository.OrderRepository;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;

// @SpringBootTest
// @AutoConfigureMockMvc
// @ActiveProfiles("test")
// public class OrderControllerIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private OrderRepository orderRepository;

//     @Test
//     @DisplayName("Test if admin can create new order")
//     @WithMockUser(username = "admin@test.com", roles = "ADMIN")
//     void shouldCreateNewOrder() throws Exception {

//         long ordersBefore = orderRepository.count();

//         String orderJson = """
//                 {
//                   "customer": {
//                     "customerId": 1
//                   },
//                   "date": "2026-01-10T17:32:24.719",
//                   "isRefunded": false,
//                   "isPaid": true
//                 }
//                 """;

//         mockMvc.perform(post("/api/orders")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(orderJson))
//                 .andExpect(status().isOk());

//         long ordersAfter = orderRepository.count();

//         assertThat(ordersAfter).isEqualTo(ordersBefore + 1);

//         Order savedOrder = orderRepository.findAll().get((int) ordersAfter - 1);
//         assertThat(savedOrder).isNotNull();
//     }
// }

package io.ggroup.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.ggroup.demo.model.Customer;
import io.ggroup.demo.model.Order;
import io.ggroup.demo.repository.CustomerRepository;
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
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Test if admin can create new order")
    @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    void shouldCreateNewOrder() throws Exception {

        long ordersBefore = orderRepository.count();

        Customer customer = new Customer();
        customer.setFirstname("Matti");
        customer.setLastname("Meikäläinen");
        customer.setEmail("matti@test.com");
        customer.setPhone("0401234567");

        Customer savedCustomer = customerRepository.save(customer);

        String orderJson = """
                {
                  "customer": {
                    "customerId": %d
                  },
                  "date": "2026-01-10T17:32:24.719",
                  "isRefunded": false,
                  "isPaid": true
                }
                """.formatted(savedCustomer.getCustomerId());

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isCreated());

        long ordersAfter = orderRepository.count();
        assertThat(ordersAfter).isEqualTo(ordersBefore + 1);

        Order savedOrder = orderRepository.findAll().get((int) ordersAfter - 1);
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getCustomer().getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
    }
}