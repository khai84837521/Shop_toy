package com.example.shop_toy.domain.item.option;

public interface ItemOptionStore {
    void store(ItemOption itemOption);

    void deleteAll();
}
