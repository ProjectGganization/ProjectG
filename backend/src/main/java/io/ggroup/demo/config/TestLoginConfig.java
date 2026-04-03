package io.ggroup.demo.config;

import io.ggroup.demo.model.User;
import io.ggroup.demo.repository.UserRepository;
import io.ggroup.demo.model.AccountStatus;
import io.ggroup.demo.repository.AccountStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class TestLoginConfig {
    // admin@test.com salasana admin123
    //seller@test salasana seller123
    //customer@test salasana customer123
    @Bean
    public CommandLineRunner createTestUsers(
            UserRepository userRepo, 
            PasswordEncoder encoder, 
            AccountStatusRepository accountStatusRepository) {
            return args -> {

            AccountStatus active =
                accountStatusRepository.findById("active")
                    .orElseGet(() -> accountStatusRepository.save(new AccountStatus("active")));

            if (userRepo.findByEmail("admin@test.com").isEmpty()) {
                // Create new admin user
                User admin = new User();
                admin.setEmail("admin@test.com");
                admin.setPasswordHash(encoder.encode("admin123"));
                admin.setAccountStatus(active);

                userRepo.save(admin);

                System.out.println("Admin user created: admin@test.com / admin123");
            }

            if (userRepo.findByEmail("seller@test.com").isEmpty()) {
                // Create new seller user
                User seller = new User();
                seller.setEmail("seller@test.com");
                seller.setPasswordHash(encoder.encode("seller123"));
                seller.setAccountStatus(active);

                userRepo.save(seller);

                System.out.println("Seller user created: seller@test.com / seller123");
            }

            if (userRepo.findByEmail("customer@test.com").isEmpty()) {
                // Create new customer user
                User customer = new User();
                customer.setEmail("customer@test.com");
                customer.setPasswordHash(encoder.encode("customer123"));
                customer.setAccountStatus(active);

                userRepo.save(customer);

                System.out.println("Customer user created: customer@test.com / customer123");
            }
        };
    }
}