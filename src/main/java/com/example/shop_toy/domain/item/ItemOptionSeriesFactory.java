package com.example.shop_toy.domain.item;




import com.example.shop_toy.domain.item.optiongroup.ItemOptionGroup;

import java.util.List;

public interface ItemOptionSeriesFactory {
    List<ItemOptionGroup> store(ItemCommand.RegisterItemRequest request, Item item);

    void deleteAll();
}
