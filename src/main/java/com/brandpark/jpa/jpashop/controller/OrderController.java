package com.brandpark.jpa.jpashop.controller;

import com.brandpark.jpa.jpashop.controller.form.OrderForm;
import com.brandpark.jpa.jpashop.domain.Order;
import com.brandpark.jpa.jpashop.dto.OrderSearch;
import com.brandpark.jpa.jpashop.service.ItemService;
import com.brandpark.jpa.jpashop.service.MemberService;
import com.brandpark.jpa.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createOrderForm(Model model) {

        model.addAttribute("members", memberService.findAll());
        model.addAttribute("items", itemService.findItems());

        return "/order/orderForm";
    }

    @PostMapping("/order")
    public String createOrder(OrderForm form) {
        orderService.order(form.getMemberId(), form.getItemId(), form.getCount());
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String list(OrderSearch orderSearch, Model model) {

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "/order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}
