package me.bruno.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Discount {

    String name;

    UUID uuid;

    double value;

    Enum<DiscountStatus> status;

    Enum<DiscountType> type;


    public final double decimalValue(double value) {
        return (value / 100);
    }
}