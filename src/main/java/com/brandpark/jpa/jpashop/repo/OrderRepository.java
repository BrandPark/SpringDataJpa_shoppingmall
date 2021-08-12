package com.brandpark.jpa.jpashop.repo;

import com.brandpark.jpa.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class OrderRepository {

    private final EntityManager em;

    public Long save(Order order) {
        if (order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }

        return order.getId();
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }
}
