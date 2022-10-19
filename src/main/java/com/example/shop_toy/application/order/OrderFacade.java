package com.example.shop_toy.application.order;


import com.example.shop_toy.domain.order.OrderCommand;
import com.example.shop_toy.domain.order.OrderInfo;
import com.example.shop_toy.domain.order.OrderService;
import com.example.shop_toy.interfaces.order.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderService orderService;

    public String registerOrder(OrderCommand.RegisterOrder registerOrder, String accessToken) {
        return orderService.registerOrder(registerOrder, accessToken);
    }

    public OrderInfo.Main retrieveOrder(String accessToken, String orderToken) {
        return orderService.retrieveOrder(accessToken, orderToken);
    }

    public OrderInfo.OrderList retrieveAllOrder(String accessToken, OrderDto.SearchOrderRequest searchRequest, Pageable pageable) {
        return orderService.retrieveAllOrder(accessToken, searchRequest, pageable);
    }
}
