package com.example.shop_toy.infrastructure.order;



import com.example.shop_toy.domain.order.item.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
