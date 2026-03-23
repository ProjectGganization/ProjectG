package io.ggroup.demo.config;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf
                .ignoringRequestMatchers(PathRequest.toH2Console())
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            .authorizeHttpRequests(auth -> auth

                // Public pages
                .requestMatchers("/", "/login", "/register", "/h2-console/**").permitAll()

                // Public GET endpoints
                .requestMatchers(HttpMethod.GET,
                    "/api/events/**",
                    "/api/tickets/**",
                    "/api/venues/**",
                    "/api/postalcodes/**",
                    "/api/issuedtickets/public/**"
                ).permitAll()

                // Public purchase
                .requestMatchers(HttpMethod.POST,
                    "/api/customers/**",
                    "/api/orders/**",
                    "/api/orderdetails/**"
                ).permitAll()

                // Logged in users can view orders and receipts
                .requestMatchers(HttpMethod.GET,
                    "/api/orders/**",
                    "/api/orderdetails/**",
                    "/api/issuedtickets/**"
                ).hasAnyRole("CUSTOMER", "SELLER", "ADMIN")

                // Customer data visible only to seller and admin
                .requestMatchers(HttpMethod.GET,
                    "/api/customers/**"
                ).hasAnyRole("SELLER", "ADMIN")

                // User management only for admin
                .requestMatchers("/api/users/**").hasRole("ADMIN")

                // Only admin can create main business resources
                .requestMatchers(HttpMethod.POST,
                    "/api/events/**",
                    "/api/tickets/**",
                    "/api/venues/**",
                    "/api/postalcodes/**"
                ).hasRole("ADMIN")

                // Seller and admin can create issued tickets
                .requestMatchers(HttpMethod.POST,
                    "/api/issuedtickets/**"
                ).hasAnyRole("SELLER", "ADMIN")

                // Only admin can edit main business resources
                .requestMatchers(HttpMethod.PUT,
                    "/api/events/**",
                    "/api/tickets/**",
                    "/api/venues/**",
                    "/api/postalcodes/**",
                    "/api/customers/**"
                ).hasRole("ADMIN")

                // Seller and admin can edit orders and receipts
                .requestMatchers(HttpMethod.PUT,
                    "/api/orders/**",
                    "/api/orderdetails/**",
                    "/api/issuedtickets/**"
                ).hasAnyRole("SELLER", "ADMIN")

                // Delete only for admin
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .logout(Customizer.withDefaults());

        return http.build();
    }
}
