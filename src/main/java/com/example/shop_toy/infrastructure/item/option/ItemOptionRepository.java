package com.example.shop_toy.infrastructure.item.option;


import com.example.shop_toy.domain.item.option.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {
}
