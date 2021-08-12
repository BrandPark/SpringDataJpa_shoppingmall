package com.brandpark.jpa.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderDate;

    @Enumerated(STRING)
    private OrderStatus status;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public static Order createOrder(Delivery delivery, Member member, OrderItem... orderItems) {
        Order order = new Order();
        order.delivery = delivery;
        order.member = member;

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.status = OrderStatus.ORDER;
        order.orderDate = LocalDateTime.now();

        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItem.updateOrder(this);
        this.orderItems.add(orderItem);
    }

    public void cancel() {
        this.status = OrderStatus.CANCEL;
        this.delivery.cancel();

        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }
}
