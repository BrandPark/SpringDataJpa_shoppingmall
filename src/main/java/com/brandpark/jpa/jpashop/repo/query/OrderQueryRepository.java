package com.brandpark.jpa.jpashop.repo.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders();

        result.forEach(o -> setOrderItemQueryDtos(o));

        return result;
    }

    public List<OrderQueryDto> findOrderQueryDtos_optimization() {

        List<OrderQueryDto> result = findOrders();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(result);

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream().map(OrderQueryDto::getOrderId).collect(Collectors.toList());

        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new com.brandpark.jpa.jpashop.repo.query.OrderItemQueryDto" +
                        "(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds"
                , OrderItemQueryDto.class
        ).setParameter("orderIds", orderIds).getResultList();

        return orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                    "select new com.brandpark.jpa.jpashop.repo.query.OrderQueryDto" +
                                "(o.id, m.name, o.orderDate, o.status, d.address)" +
                            " from Order o" +
                            " join o.member m" +
                            " join o.delivery d"
                    , OrderQueryDto.class
            ).getResultList();
    }

    private void setOrderItemQueryDtos(OrderQueryDto o) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new com.brandpark.jpa.jpashop.repo.query.OrderItemQueryDto" +
                        "(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId"
                , OrderItemQueryDto.class
        ).setParameter("orderId", o.getOrderId()).getResultList();

        o.setOrderItems(orderItems);
    }

    public List<OrderQueryFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new com.brandpark.jpa.jpashop.repo.query.OrderQueryFlatDto" +
                        "(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderQueryFlatDto.class
        ).getResultList();
    }
}
