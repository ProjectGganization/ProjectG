package io.ggroup.demo.repository;

import io.ggroup.demo.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketTypeRepository extends JpaRepository<TicketType, String> {
}
