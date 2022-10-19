package com.example.shop_toy.domain.order;


import com.example.shop_toy.interfaces.order.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderReader {
    Order getOrder(String orderToken);

    Page<Order> getOrderList(String memberId, OrderDto.SearchOrderRequest searchRequest, Pageable pageable);
}
