package com.example.shop_toy.infrastructure.item.optiongroup;


import com.example.shop_toy.domain.item.optiongroup.ItemOptionGroup;
import com.example.shop_toy.domain.item.optiongroup.ItemOptionGroupStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemOptionGroupStoreImpl implements ItemOptionGroupStore {

    private final ItemOptionGroupRepository itemOptionGroupRepository;

    @Override
    public ItemOptionGroup store(ItemOptionGroup itemOptionGroup) {
        return itemOptionGroupRepository.save(itemOptionGroup);
    }

    @Override
    public void deleteAll() {
        itemOptionGroupRepository.deleteAll();
    }
}
