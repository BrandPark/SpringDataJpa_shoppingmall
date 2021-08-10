package com.brandpark.jpa.jpashop.domain;

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
}
