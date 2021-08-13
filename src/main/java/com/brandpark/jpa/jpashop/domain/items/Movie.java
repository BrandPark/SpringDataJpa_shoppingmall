package com.brandpark.jpa.jpashop.domain.items;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("M")
@Getter
@NoArgsConstructor
@Entity
public class Movie extends Item {
    private String director;
    private String actor;
}
