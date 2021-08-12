package com.brandpark.jpa.jpashop.repo;

import com.brandpark.jpa.jpashop.domain.items.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class ItemRepository {

    private final EntityManager em;

    public Long save(Item item) {
        if(item.getId() == null)
            em.persist(item);
        else
            em.merge(item);
        return item.getId();
    }

    public Item findOne(Long itemId) {
        return em.find(Item.class, itemId);
    }
}
