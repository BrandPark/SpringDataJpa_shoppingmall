package com.brandpark.jpa.jpashop.api;

import com.brandpark.jpa.jpashop.domain.Address;
import com.brandpark.jpa.jpashop.domain.Order;
import com.brandpark.jpa.jpashop.domain.OrderItem;
import com.brandpark.jpa.jpashop.domain.OrderStatus;
import com.brandpark.jpa.jpashop.repo.OrderRepository;
import com.brandpark.jpa.jpashop.repo.query.OrderItemQueryDto;
import com.brandpark.jpa.jpashop.repo.query.OrderQueryDto;
import com.brandpark.jpa.jpashop.repo.query.OrderQueryFlatDto;
import com.brandpark.jpa.jpashop.repo.query.OrderQueryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    /**
     * Entity 를 직접 리턴
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll();

        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getStatus();
            order.getOrderItems().stream().forEach(oi -> oi.getItem().getName());
        }

        return all;
    }

    /**
     * DTO 로 변환하여 리턴
     */
    @GetMapping("/api/v2/orders")
    public List<OrderResponseDto> ordersV2() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(OrderResponseDto::new).collect(toList());
    }

    /**
     * 모든 연관 필드를 fetch join 을 사용하여 로드하고 DTO 로 변환하여 리턴
     */
    @GetMapping("/api/v3/orders")
    public List<OrderResponseDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithItem();

        return orders.stream().map(OrderResponseDto::new).collect(toList());
    }

    /**
     * 다대일 관계 필드만 fetch join 을 사용하여 로드하고, 컬렉션 연관 필드는 지연로딩으로 로드
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderResponseDto> ordersV3_paging(@RequestParam(defaultValue = "0") int offset,
                                           @RequestParam(defaultValue = "1000") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        return orders.stream().map(OrderResponseDto::new).collect(toList());
    }

    /**
     * JPA 로 DTO 직접 조회. 컬렉션 DTO 는 루프를 통해 초기화. N + 1 문제 발생
     */
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDtos();
    }

    /**
     * IN 쿼리를 사용하여 v4에서 N + 1 문제 해결
     */
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findOrderQueryDtos_optimization();
    }

    /**
     * Flat 한 DTO 로 데이터를 한번에 조회하고 API 스펙에 맞게 변환하여 출력
     */
    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderQueryFlatDto> flats = orderQueryRepository.findAllByDto_flat();
        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(toList());
    }

    @Setter
    @Getter
    private static class OrderResponseDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemResponseDto> orderItems;

        public OrderResponseDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems().stream().map(OrderItemResponseDto::new).collect(toList());
        }
    }

    @Setter
    @Getter
    private static class OrderItemResponseDto {
        private String itemName;//상품 명
        private int orderPrice; //주문 가격
        private int count; //주문 수량

        public OrderItemResponseDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
