package com.example.shop_toy.domain.item;


import com.example.shop_toy.domain.item.option.ItemOption;
import com.example.shop_toy.domain.item.optiongroup.ItemOptionGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class ItemInfo {

    @Getter
    @ToString
    public static class Main {
        private final String itemToken;
        private final String memberId;
        private final String itemName;
        private final Long itemPrice;
        private final List<ItemOptionGroupInfo> itemOptionGroupList;

        public Main(Item item, List<ItemOptionGroupInfo> itemOptionGroupInfoList) {
            this.itemToken = item.getItemToken();
            this.memberId = item.getMemberId();
            this.itemName = item.getItemName();
            this.itemPrice = item.getItemPrice();
            this.itemOptionGroupList = itemOptionGroupInfoList;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class ItemList {
        private final int page;
        private final int totalPage;
        private final int size;
        private final String keyword;
        private final List<Main> itemInfoList;
    }

    @Getter
    @ToString
    public static class ItemOptionGroupInfo {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<ItemOptionInfo> itemOptionList;

        public ItemOptionGroupInfo(ItemOptionGroup itemOptionGroup, List<ItemOptionInfo> itemOptionList) {
            this.ordering = itemOptionGroup.getOrdering();
            this.itemOptionGroupName = itemOptionGroup.getItemOptionGroupName();
            this.itemOptionList = itemOptionList;
        }
    }

    @Getter
    @ToString
    public static class ItemOptionInfo {
        private final Integer ordering;
        private final String itemOptionName;
        private final Long itemOptionPrice;

        public ItemOptionInfo(ItemOption itemOption) {
            this.ordering = itemOption.getOrdering();
            this.itemOptionName = itemOption.getItemOptionName();
            this.itemOptionPrice = itemOption.getItemOptionPrice();
        }
    }
}