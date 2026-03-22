package io.ggroup.demo.config;

import io.ggroup.demo.model.User;
import io.ggroup.demo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class TestLoginConfig {
    // Tämä metodi luo käyttäjän admin@test.com salasanalla admin123. 
    @Bean
    public org.springframework.boot.CommandLineRunner createAdmin(UserRepository userRepo,
                                                                  PasswordEncoder encoder) {
        return args -> {

            // Check if admin user already exists
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
}