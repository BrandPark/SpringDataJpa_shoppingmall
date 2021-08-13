package com.brandpark.jpa.jpashop.domain.items;

import com.brandpark.jpa.jpashop.domain.Category;
import com.brandpark.jpa.jpashop.exception.NotEnoughStockException;
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

    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int modStock = this.stockQuantity - quantity;
        if (modStock < 0) {
            throw new NotEnoughStockException("감소하려는 양이 재고보다 많습니다");
        }
        this.stockQuantity = modStock;
    }

    protected Item(Long id, String name, int price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
