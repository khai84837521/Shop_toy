package com.example.shop_toy.domain.item;

public interface ItemStore {
    Item store(Item initItem);

    void deleteAll();
}
