package com.example.shop_toy.domain.order;


import com.example.shop_toy.domain.order.item.OrderItem;
import com.example.shop_toy.domain.order.item.OrderItemOption;
import com.example.shop_toy.domain.order.item.OrderItemOptionGroup;

public interface OrderStore {
    Order store(Order order);
    OrderItem store(OrderItem orderItem);
    OrderItemOptionGroup store(OrderItemOptionGroup orderItemOptionGroup);
    OrderItemOption store(OrderItemOption orderItemOption);
}
