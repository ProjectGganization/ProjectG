package io.ggroup.demo.repository;

import io.ggroup.demo.model.SalesSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesSessionRepository extends JpaRepository<SalesSession, Integer> {
}
