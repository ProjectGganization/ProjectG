package io.ggroup.demo.repository;

import io.ggroup.demo.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetails.OrderDetailsId> {
}
