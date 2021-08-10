package com.brandpark.jpa.jpashop.domain.items;

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
}
