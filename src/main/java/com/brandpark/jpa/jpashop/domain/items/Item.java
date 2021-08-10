package com.brandpark.jpa.jpashop.domain.items;

import com.brandpark.jpa.jpashop.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@NoArgsConstructor
@Getter
@Inheritance(strategy = SINGLE_TABLE)
@Entity
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
