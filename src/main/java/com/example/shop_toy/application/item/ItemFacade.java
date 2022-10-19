package com.example.shop_toy.application.item;


import com.example.shop_toy.domain.item.ItemCommand;
import com.example.shop_toy.domain.item.ItemInfo;
import com.example.shop_toy.domain.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemFacade {
    private final ItemService itemService;

    public String registerItem(ItemCommand.RegisterItemRequest request, String accessToken) {
        return itemService.registerItem(request, accessToken);
    }

    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        return itemService.retrieveItemInfo(itemToken);
    }

    public void deleteAllItem() {
        itemService.deleteAllItem();
    }

    public ItemInfo.ItemList retrieveAllItemInfo(String keyword, Pageable pageable) {
        return itemService.retrieveAllItemInfo(keyword, pageable);
    }
}
