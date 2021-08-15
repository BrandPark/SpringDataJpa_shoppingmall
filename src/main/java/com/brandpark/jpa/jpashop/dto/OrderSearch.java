package com.brandpark.jpa.jpashop.dto;

import com.brandpark.jpa.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
