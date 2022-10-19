package com.example.shop_toy.domain.item.optiongroup;

public interface ItemOptionGroupStore {
    ItemOptionGroup store(ItemOptionGroup itemOptionGroup);

    void deleteAll();
}
