package io.ggroup.demo.config;

import io.ggroup.demo.model.User;
import io.ggroup.demo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class TestLoginConfig {
    // admin@test.com salasana admin123
    //seller@test salasana seller123
    //customer@test salasana customer123
    @Bean
    public org.springframework.boot.CommandLineRunner createAdmin(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {

            if (userRepo.findByEmail("admin@test.com").isEmpty()) {
                // Create new admin user
                User admin = new User();
                admin.setEmail("admin@test.com");
                admin.setPasswordHash(encoder.encode("admin123"));
                admin.setAccountCreated(LocalDate.now());

                userRepo.save(admin);

                System.out.println("Admin user created: admin@test.com / admin123");
            }
        };
    }

    @Bean
    public org.springframework.boot.CommandLineRunner createSeller(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {

            if (userRepo.findByEmail("seller@test.com").isEmpty()) {
                // Create new seller user
                User seller = new User();
                seller.setEmail("seller@test.com");
                seller.setPasswordHash(encoder.encode("seller123"));
                seller.setAccountCreated(LocalDate.now());

                userRepo.save(seller);

                System.out.println("Seller user created: seller@test.com / seller123");
            }
        };
    }

    @Bean
    public org.springframework.boot.CommandLineRunner createCustomer(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {

            if (userRepo.findByEmail("customer@test.com").isEmpty()) {
                // Create new customer user
                User customer = new User();
                customer.setEmail("customer@test.com");
                customer.setPasswordHash(encoder.encode("customer123"));
                customer.setAccountCreated(LocalDate.now());

                userRepo.save(customer);

                System.out.println("Customer user created: customer@test.com / customer123");
            }
        };
    }
}