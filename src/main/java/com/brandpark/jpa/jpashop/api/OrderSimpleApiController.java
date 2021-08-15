package com.brandpark.jpa.jpashop.api;

import com.brandpark.jpa.jpashop.api.simplequery.OrderSimpleQueryDto;
import com.brandpark.jpa.jpashop.domain.Address;
import com.brandpark.jpa.jpashop.domain.Order;
import com.brandpark.jpa.jpashop.domain.OrderStatus;
import com.brandpark.jpa.jpashop.dto.OrderSearch;
import com.brandpark.jpa.jpashop.repo.OrderRepository;
import com.brandpark.jpa.jpashop.repo.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * Entity 를 사용하여 모든 주문정보(Order + Member + Delivery) 조회 API
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getStatus();
        }

        return orders;
    }

    /**
     * DTO 를 사용하여 모든 주문정보(Order + Member + Delivery) 조회 API
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
    }

    /**
     * DTO 와  JOIN FETCH 를 사용하여 모든 주문정보(Order + Member + Delivery) 조회 API
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream().map(SimpleOrderDto::new).collect(Collectors.toList());
    }

    /**
     * DTO 직접 조회를 사용하여 모든 주문정보(Order + Member + Delivery) 조회 API
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    private static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
