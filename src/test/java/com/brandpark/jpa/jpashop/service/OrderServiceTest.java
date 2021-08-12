package com.brandpark.jpa.jpashop.service;

import com.brandpark.jpa.jpashop.domain.*;
import com.brandpark.jpa.jpashop.domain.items.Book;
import com.brandpark.jpa.jpashop.domain.items.Item;
import com.brandpark.jpa.jpashop.exception.NotEnoughStockException;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    EntityManager em;

    @Test
    public void 주문이_된다() throws Exception {
        // given
        Member member = createMember();
        em.persist(member);

        int stockQuantity = 100;
        Item item = createBook(stockQuantity);
        em.persist(item);

        // when
        int orderCount = 20;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then
        Order order = em.find(Order.class, orderId);
        Delivery delivery = order.getDelivery();
        Member orderMember = order.getMember();

        assertThat(order.getStatus()).as("상품 주문시 주문상태는 ORDER 다.").isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems().size()).as("주문한 상품의 종류 수가 정확해야 한다.").isEqualTo(1);
        assertThat(order.getOrderItems().get(0).getTotalPrice())
                .as("주문한 상품들 중 x에 대한 총금액은 [x의 가격 * x의 개수]이다.")
                .isEqualTo(order.getOrderItems().get(0).getCount() * order.getOrderItems().get(0).getOrderPrice());
        assertThat(order.getTotalPrice()).as("주문의 총 금액은 상품들 금액의 합이다.")
                .isEqualTo(order.getOrderItems().get(0).getTotalPrice());
        assertThat(item.getStockQuantity()).as("주문 수량만큼 재고가 줄어야 한다.")
                .isEqualTo(stockQuantity - orderCount);
        assertThat(delivery.getStatus()).as("상품 주문시 배송상태는 READY 다.").isEqualTo(DeliveryStatus.READY);
        assertThat(delivery.getAddress()).as("상품 주문시 배송지는 주문회원의 주소이다.").isEqualTo(orderMember.getAddress());
    }

    @Test
    public void 주문을_취소한다() throws Exception {
        // given
        Member member = createMember();
        em.persist(member);

        int stockQuantity = 100;
        Item item = createBook(stockQuantity);
        em.persist(item);

        int orderCount = 20;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order cancelOrder = em.find(Order.class, orderId);
        assertThat(cancelOrder.getStatus()).as("주문이 취소되면 상태는 CANCEL이 된다.").isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).as("주문이 취소되면 재고에 반영되어야 한다.").isEqualTo(stockQuantity);
    }

    @Test(expected = NotEnoughStockException.class)
    public void 재고수량_초과_주문은_예외발생() throws Exception {
        // given
        Member member = createMember();
        em.persist(member);

        int stockQuantity = 100;
        Item item = createBook(stockQuantity);
        em.persist(item);

        // when
        int overQuantity = stockQuantity + 10;
        orderService.order(member.getId(), item.getId(), overQuantity);

        // then
        fail("예외가 발생해야 한다");
    }

    @Test(expected = IllegalStateException.class)
    public void 배송완료한_주문을_취소하면_예외발생() {
        // given
        Member member = createMember();
        em.persist(member);

        Item item = createBook(100);
        em.persist(item);
        Long orderId = orderService.order(member.getId(), item.getId(), 20);
        Order order = em.find(Order.class, orderId);

        // when
        order.getDelivery().complete();
        orderService.cancelOrder(orderId);

        // then
        fail("예외가 발생해야 한다.");
    }

    private Member createMember() {
        return Member.builder()
                .name("mingon")
                .address(new Address("서울", "서대문구", "111-111"))
                .build();
    }

    private Book createBook(int quantity) {
        return Book.builder()
                .name("책")
                .stockQuantity(quantity)
                .price(10000)
                .author("writer")
                .isbn("isbn_book0")
                .build();
    }
}