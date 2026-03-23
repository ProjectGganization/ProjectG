package io.ggroup.demo.repository;

import io.ggroup.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Spring auto-generates: SELECT * FROM users WHERE email = ?    
    Optional<User> findByEmail(String email);
}