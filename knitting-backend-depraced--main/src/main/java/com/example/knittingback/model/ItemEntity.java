package com.example.knittingback.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemEntity {
    private long id;
    private String name;
    private String imagePath;
    private String description;
    private BigDecimal price;

}
