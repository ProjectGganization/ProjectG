package io.ggroup.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ggroup.demo.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetails.OrderDetailsId> {

    /* boolean isEmpty();

    boolean exists(Integer id); */

    // void deleteById(Integer id);

    // public void deleteById(Integer id);
}
