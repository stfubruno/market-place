package me.bruno.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Discount {

    String ID;
    double percentage;
    Enum<DiscountStatus> status;
}
