package io.ggroup.demo.repository;

import io.ggroup.demo.model.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // Spring auto-generates: SELECT * FROM customers WHERE email = ?
    Optional<Customer> findByEmail(String email);

    // Spring auto-generates: SELECT * FROM customers WHERE lastname = ?
    List<Customer> findByLastname(String lastname);
}
