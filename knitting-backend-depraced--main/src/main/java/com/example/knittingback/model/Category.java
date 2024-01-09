package com.example.knittingback.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
//@Builder
@NoArgsConstructor
public class Category {
    private long id;
    private int number;
    private String image;
    private String name;
    private List<ItemEntity> itemEntities;

    public Category(long id, int number, String image, String name) {
        this.id = id;
        this.number = number;
        this.image = image;
        this.name = name;
    }
}
