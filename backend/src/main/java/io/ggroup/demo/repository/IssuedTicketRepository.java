package io.ggroup.demo.repository;

import io.ggroup.demo.model.IssuedTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedTicketRepository extends JpaRepository<IssuedTicket, Integer> {
}
