package me.bruno.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Product {

    String name;

    @Setter
    double price;

    long code;

    @Setter
    int amount;

    boolean ageRestriction;
}