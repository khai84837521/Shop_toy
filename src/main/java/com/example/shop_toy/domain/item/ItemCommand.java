package com.example.shop_toy.domain.item;


import com.example.shop_toy.domain.item.option.ItemOption;
import com.example.shop_toy.domain.item.optiongroup.ItemOptionGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class ItemCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterItemRequest {
        private final String itemName;
        private final Long itemPrice;
        private final List<RegisterItemOptionGroupRequest> itemOptionGroupRequestList;

        public Item toEntity(String memberId) {
            return Item.builder()
                    .memberId(memberId)
                    .itemName(itemName)
                    .itemPrice(itemPrice)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterItemOptionGroupRequest {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<RegisterItemOptionRequest> itemOptionRequestList;

        public ItemOptionGroup toEntity(Item item) {
            return ItemOptionGroup.builder()
                    .item(item)
                    .ordering(ordering)
                    .itemOptionGroupName(itemOptionGroupName)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterItemOptionRequest {
        private final Integer ordering;
        private final String itemOptionName;
        private final Long itemOptionPrice;

        public ItemOption toEntity(ItemOptionGroup itemOptionGroup) {
            return ItemOption.builder()
                    .itemOptionGroup(itemOptionGroup)
                    .ordering(ordering)
                    .itemOptionName(itemOptionName)
                    .itemOptionPrice(itemOptionPrice)
                    .build();
        }
    }
}
