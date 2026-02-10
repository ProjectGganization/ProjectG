package io.ggroup.demo.repository;

import io.ggroup.demo.model.PostalCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostalCodeRepository extends JpaRepository<PostalCode, String> {
}
