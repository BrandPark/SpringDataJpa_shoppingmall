package com.brandpark.jpa.jpashop.dto;

import com.brandpark.jpa.jpashop.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
