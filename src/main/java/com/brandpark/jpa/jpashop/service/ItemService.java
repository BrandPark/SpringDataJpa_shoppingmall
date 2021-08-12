package com.brandpark.jpa.jpashop.service;

import com.brandpark.jpa.jpashop.domain.items.Item;
import com.brandpark.jpa.jpashop.repo.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final EntityManager em;

    @Transactional
    public Long saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItems() {
        return em.createQuery("select i from Item i").getResultList();
    }
}
