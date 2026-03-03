package io.ggroup.demo.repository;

import io.ggroup.demo.model.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusRepository extends JpaRepository<EventStatus, String> {
}
