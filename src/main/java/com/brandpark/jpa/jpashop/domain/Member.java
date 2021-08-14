package com.brandpark.jpa.jpashop.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @Builder
    protected Member(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public void update(String name) {
        this.name = name;
    }
}
