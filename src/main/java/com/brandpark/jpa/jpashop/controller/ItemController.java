package com.brandpark.jpa.jpashop.controller;

import com.brandpark.jpa.jpashop.controller.form.BookForm;
import com.brandpark.jpa.jpashop.domain.items.Book;
import com.brandpark.jpa.jpashop.domain.items.Item;
import com.brandpark.jpa.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/items")
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/new")
    public String createForm(Model model) {

        model.addAttribute("form", new BookForm());
        return "/items/createItemForm";
    }

    @PostMapping("/new")
    public String create(BookForm form) {

        Book book = Book.builder()
                .name(form.getName())
                .price(form.getPrice())
                .stockQuantity(form.getStockQuantity())
                .author(form.getAuthor())
                .isbn(form.getIsbn())
                .build();

        itemService.saveItem(book);

        return "redirect:/items";
    }

    @GetMapping
    public String list(Model model) {

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model) {

        Book book = (Book)itemService.findOne(itemId);

        BookForm bookForm = BookForm.builder()
                .id(book.getId())
                .name(book.getName())
                .price(book.getPrice())
                .stockQuantity(book.getStockQuantity())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .build();

        model.addAttribute("form", bookForm);

        return "items/updateItemForm";
    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form) {

        Book book = Book.builder()
                .id(form.getId())
                .name(form.getName())
                .price(form.getPrice())
                .stockQuantity(form.getStockQuantity())
                .author(form.getAuthor())
                .isbn(form.getIsbn())
                .build();

        itemService.saveItem(book);

        return "redirect:/items";
    }
}
