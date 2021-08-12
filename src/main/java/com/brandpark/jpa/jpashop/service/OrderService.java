package com.brandpark.jpa.jpashop.service;

import com.brandpark.jpa.jpashop.domain.*;
import com.brandpark.jpa.jpashop.domain.items.Item;
import com.brandpark.jpa.jpashop.dto.OrderSearch;
import com.brandpark.jpa.jpashop.repo.ItemRepository;
import com.brandpark.jpa.jpashop.repo.MemberRepository;
import com.brandpark.jpa.jpashop.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .status(DeliveryStatus.READY)
                .build();

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(delivery, member, orderItem);

        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> searchOrders(OrderSearch orderSearch) {
        // TODO
        return null;
    }
}
