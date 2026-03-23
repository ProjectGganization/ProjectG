package io.ggroup.demo.repository;

import io.ggroup.demo.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    // Spring auto-generates: SELECT * FROM sellers WHERE email = ?    
    Optional<Seller> findByEmail(String email);
}