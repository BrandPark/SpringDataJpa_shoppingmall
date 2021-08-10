package com.brandpark.jpa.jpashop.domain.items;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("A")
@Getter
@NoArgsConstructor
@Entity
public class Album extends Item {
    private String artist;
    private String etc;
}
