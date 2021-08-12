package com.brandpark.jpa.jpashop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private DeliveryStatus status;

    @OneToOne(fetch = LAZY, mappedBy = "delivery")
    private Order order;

    public void updateOrder(Order order) {
        this.order = order;
    }

    public void cancel() {
        if (this.status == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.status = DeliveryStatus.CANCEL;
    }

    public void complete(){
        this.status = DeliveryStatus.COMP;
    }
    @Builder
    private Delivery(Address address, DeliveryStatus status, Order order) {
        this.address = address;
        this.status = status;
        this.order = order;
    }
}
