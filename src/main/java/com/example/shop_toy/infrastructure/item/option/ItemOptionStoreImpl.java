package com.example.shop_toy.infrastructure.item.option;


import com.example.shop_toy.domain.item.option.ItemOption;
import com.example.shop_toy.domain.item.option.ItemOptionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemOptionStoreImpl implements ItemOptionStore {

    private final ItemOptionRepository itemOptionRepository;

    @Override
    public void store(ItemOption itemOption) {
        itemOptionRepository.save(itemOption);
    }

    @Override
    public void deleteAll() {
        itemOptionRepository.deleteAll();
    }
}
