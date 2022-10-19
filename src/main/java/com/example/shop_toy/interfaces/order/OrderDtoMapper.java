package com.example.shop_toy.interfaces.order;


import com.example.shop_toy.domain.order.OrderCommand;
import com.example.shop_toy.domain.order.OrderInfo;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrderDtoMapper {

    @Mappings({
            @Mapping(source = "orderedAt", target = "orderedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    })
    OrderDto.Main of(OrderInfo.Main mainResult);

    OrderDto.DeliveryInfo of(OrderInfo.DeliveryInfo deliveryResult);

    OrderDto.OrderItem of(OrderInfo.OrderItem orderItemResult);

    OrderCommand.RegisterOrder of(OrderDto.RegisterOrderRequest request);

    OrderCommand.RegisterOrderItem of(OrderDto.RegisterOrderItemRequest request);

    OrderCommand.RegisterOrderItemOptionGroup of(OrderDto.RegisterOrderItemOptionGroupRequest request);

    OrderCommand.RegisterOrderItemOption of(OrderDto.RegisterOrderItemOptionRequest request);

    OrderDto.RegisterResponse of(String orderToken);

    OrderDto.OrderListResponse of(OrderInfo.OrderList orderListInfo);

}
