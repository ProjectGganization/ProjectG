package io.ggroup.demo.repository;

import io.ggroup.demo.model.IssuedTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IssuedTicketRepository extends JpaRepository<IssuedTicket, Integer> {
    Optional<IssuedTicket> findByQrCode(String qrCode);
}
