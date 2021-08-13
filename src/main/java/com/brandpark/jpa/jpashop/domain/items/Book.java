package com.brandpark.jpa.jpashop.domain.items;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("B")
@Getter
@NoArgsConstructor
@Entity
public class Book extends Item {
    private String author;
    private String isbn;

    @Builder
    public Book(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        super(id, name, price, stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }
}
