package me.bruno.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Discount {

    UUID uuid;

    String name;

    double value;

    Enum<DiscountStatus> status;

    Enum<DiscountType> type;

    public Discount(UUID uuid, String name, double value, String type) {
        this(uuid, name, value, DiscountStatus.ACTIVE, DiscountType.valueOf(type.toUpperCase()));
    }

    public final double decimalValue(double value) {
        return (value / 100);
    }
}