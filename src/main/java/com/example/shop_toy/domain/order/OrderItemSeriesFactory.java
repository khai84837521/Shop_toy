package com.example.shop_toy.domain.order;




import com.example.shop_toy.domain.order.item.OrderItem;

import java.util.List;

public interface OrderItemSeriesFactory {
    List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder);
}
