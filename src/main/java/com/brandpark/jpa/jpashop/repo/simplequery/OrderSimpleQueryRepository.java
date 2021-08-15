package com.brandpark.jpa.jpashop.repo.simplequery;

import com.brandpark.jpa.jpashop.api.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new com.brandpark.jpa.jpashop.api.simplequery.OrderSimpleQueryDto(" +
                        "o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o join o.member m join o.delivery d", OrderSimpleQueryDto.class).getResultList();
    }
}