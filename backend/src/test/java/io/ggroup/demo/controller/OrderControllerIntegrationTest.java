package io.ggroup.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.ggroup.demo.model.AccountStatus;
import io.ggroup.demo.model.Customer;
import io.ggroup.demo.model.Order;
import io.ggroup.demo.model.Seller;
import io.ggroup.demo.model.User;
import io.ggroup.demo.repository.AccountStatusRepository;
import io.ggroup.demo.repository.CustomerRepository;
import io.ggroup.demo.repository.OrderRepository;
import io.ggroup.demo.repository.SellerRepository;
import io.ggroup.demo.repository.UserRepository;
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

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountStatusRepository accountStatusRepository;

    @Test
    @DisplayName("Test if admin can create new order")
    // @WithMockUser(username = "admin@test.com", roles = "ADMIN")
    @WithMockUser(username = "customer@test.com", roles = "CUSTOMER")
    void shouldCreateNewOrder() throws Exception {

        long ordersBefore = orderRepository.count();

        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setAccountStatus("active");
        accountStatusRepository.save(accountStatus);

        User user = new User();
        user.setEmail("selleruser@test.com");
        user.setPasswordHash("hashedpassword");
        user.setAccountStatus(accountStatus);
        User savedUser = userRepository.save(user);

        Seller seller = new Seller();
        seller.setName("Test Seller");
        seller.setEmail("seller@test.com");
        seller.setPhone("0407654321");
        seller.setUser(savedUser);
        Seller savedSeller = sellerRepository.save(seller);

        Customer customer = new Customer();
        customer.setFirstname("Matti");
        customer.setLastname("Meikäläinen");
        customer.setEmail("matti@test.com");
        customer.setPhone("0401234567");
        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer.getCustomerId()).isNotNull();
        assertThat(savedSeller.getSellerId()).isNotNull();

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
                """.formatted(savedCustomer.getCustomerId(), savedSeller.getSellerId());

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isCreated());

        long ordersAfter = orderRepository.count();
        assertThat(ordersAfter).isEqualTo(ordersBefore + 1);

        Order savedOrder = orderRepository.findAll().get((int) ordersAfter - 1);
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getCustomer().getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
        assertThat(savedOrder.getSeller().getSellerId()).isEqualTo(savedSeller.getSellerId());
    }
}