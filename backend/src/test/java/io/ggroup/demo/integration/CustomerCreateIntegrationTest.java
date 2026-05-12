package io.ggroup.demo.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.ggroup.demo.model.Customer;
import io.ggroup.demo.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerCreateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("User can create a new customer")
    void shouldCreateNewCustomer() throws Exception {

        long customersBefore = customerRepository.count();

        String uniqueEmail = "customer_" + System.currentTimeMillis() + "@test.com";

        String customerJson = """
                {
                  "firstname": "Matti",
                  "lastname": "Meikäläinen",
                  "email": "%s",
                  "phone": "0401234567"
                }
                """.formatted(uniqueEmail);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson))
                .andExpect(status().isCreated());

        assertThat(customerRepository.count()).isEqualTo(customersBefore + 1);

        Customer savedCustomer = customerRepository.findAll().get((int) customerRepository.count() - 1);
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getFirstname()).isEqualTo("Matti");
        assertThat(savedCustomer.getLastname()).isEqualTo("Meikäläinen");
        assertThat(savedCustomer.getEmail()).isEqualTo(uniqueEmail);
        assertThat(savedCustomer.getPhone()).isEqualTo("0401234567");
    }
}