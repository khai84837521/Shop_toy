package com.example.shop_toy.infrastructure.item;



import com.example.shop_toy.domain.item.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemToken(String itemToken);
    Page<Item> findItemByItemNameContaining(String keyword, Pageable pageable);
}
