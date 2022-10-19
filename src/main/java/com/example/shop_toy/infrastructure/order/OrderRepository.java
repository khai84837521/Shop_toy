package com.example.shop_toy.infrastructure.order;



import com.example.shop_toy.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderToken(String orderToken);
    Page<Order> findByMemberIdAndStatusAndCreatedAtBetween(String memberId, Order.Status status, ZonedDateTime start, ZonedDateTime end, Pageable pageable);
}
