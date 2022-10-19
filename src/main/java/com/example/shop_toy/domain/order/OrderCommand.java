package com.example.shop_toy.domain.order;


import com.example.shop_toy.domain.item.Item;
import com.example.shop_toy.domain.order.fragment.DeliveryFragment;
import com.example.shop_toy.domain.order.item.OrderItem;
import com.example.shop_toy.domain.order.item.OrderItemOption;
import com.example.shop_toy.domain.order.item.OrderItemOptionGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class OrderCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterOrder {
        private final String receiverName;
        private final String receiverPhone;
        private final String receiverZipcode;
        private final String receiverAddress1;
        private final String receiverAddress2;
        private final String etcMessage;
        private final List<RegisterOrderItem> orderItemList;

        public Order toEntity(String memberId) {
            var deliveryFragment = DeliveryFragment.builder()
                    .receiverName(receiverName)
                    .receiverPhone(receiverPhone)
                    .receiverZipcode(receiverZipcode)
                    .receiverAddress1(receiverAddress1)
                    .receiverAddress2(receiverAddress2)
                    .etcMessage(etcMessage)
                    .build();

            return Order.builder()
                    .memberId(memberId)
                    .deliveryFragment(deliveryFragment)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderItem {
        private final Integer orderCount;
        private final String itemToken;
        private final String itemName;
        private final Long itemPrice;
        private final List<RegisterOrderItemOptionGroup> orderItemOptionGroupList;

        public OrderItem toEntity(Order order, Item item) {
            return OrderItem.builder()
                    .order(order)
                    .orderCount(orderCount)
                    .memberId(item.getMemberId())
                    .itemId(item.getId())
                    .itemToken(itemToken)
                    .itemName(itemName)
                    .itemPrice(itemPrice)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderItemOptionGroup {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<RegisterOrderItemOption> orderItemOptionList;

        public OrderItemOptionGroup toEntity(OrderItem orderItem) {
            return OrderItemOptionGroup.builder()
                    .orderItem(orderItem)
                    .ordering(ordering)
                    .itemOptionGroupName(itemOptionGroupName)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderItemOption {
        private final Integer ordering;
        private final String itemOptionName;
        private final Long itemOptionPrice;

        public OrderItemOption toEntity(OrderItemOptionGroup orderItemOptionGroup) {
            return OrderItemOption.builder()
                    .orderItemOptionGroup(orderItemOptionGroup)
                    .ordering(ordering)
                    .itemOptionName(itemOptionName)
                    .itemOptionPrice(itemOptionPrice)
                    .build();
        }
    }
}
