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
    int units;

    boolean ageRestriction;

    public void incrementUnits(int value) {
        this.units += value;
    }

    public void decrementUnits(int value) {
        if (value <= 0) return;

        //Ensure value does not go below 0
        this.units = Math.max(0, this.units - value);
    }
}