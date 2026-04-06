package io.ggroup.demo.service;

import io.ggroup.demo.repository.UserRepository;
import io.ggroup.demo.repository.SellerRepository;
import io.ggroup.demo.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final CustomerRepository customerRepository;

    public UsersDetailsService(UserRepository userRepository,
                               SellerRepository sellerRepository,
                               CustomerRepository customerRepository) {
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Check if email is admin email
        if (email.equals("admin@test.com")) {
            var admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No account found with email: " + email));

            return User.withUsername(admin.getEmail())
                    .password(admin.getPasswordHash())
                    .roles("ADMIN")
                    .build();
        }

        // Search sellers table
        var seller = sellerRepository.findByEmail(email);
        if (seller.isPresent()) {
            return User.withUsername(seller.get().getEmail())
                    .password(seller.get().getUser().getPasswordHash())
                    .roles("SELLER")
                    .build();
        }

        // Search customers table
        var customer = customerRepository.findByEmail(email);
        if (customer.isPresent()) {
            return User.withUsername(customer.get().getEmail())
                    .password(customer.get().getUser().getPasswordHash())
                    .roles("CUSTOMER")
                    .build();
        }

        // Search users table
        var user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return User.withUsername(user.get().getEmail())
                    .password(user.get().getPasswordHash())
                    .roles("USER")
                    .build();
        }

        throw new UsernameNotFoundException("No account found with email: " + email);
    }
}