package me.bruno.discount;

import me.bruno.basket.Basket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DiscountHandler {
    public static List<Discount> discounts = new ArrayList<>();

    public static void createDiscount(String name, DiscountType type, int value, Date expireDate) {
        UUID uuid = UUID.randomUUID();

        //duplicate prevention
        for (Discount discount : discounts) {
            if (discount.getUuid() == uuid) {
                uuid = UUID.randomUUID();
            }
        }

        Discount discount = new Discount(name, uuid, value, DiscountStatus.ACTIVE, type, expireDate);
        discounts.add(discount);
        System.out.println("Successfully registered discount >> " + name + " <<");
    }

    public static void removeDiscount(Discount discount) {
        for (Discount discountsArray : discounts) {
            if (discount.getUuid() == discountsArray.getUuid()) {
                discounts.remove(discount);
                System.out.println("Successfully unregistered discount >> " + discount.getName() + " <<");
            }
        }
    }

    public static void applyDiscount(Basket basket, Discount discount) {
        for (Discount discountsArray : discounts) {
            if (discountsArray.getUuid() == discount.getUuid() && discount.status == DiscountStatus.ACTIVE) {
                switch (discount.getType()) {
                    case DiscountType.PERCENTAGE -> basket.setValue(basket.getValue() - (basket.getValue() * discount.decimalValue(discount.getValue())));
                    case DiscountType.VALUE -> basket.setValue(basket.getValue() - discount.getValue());
                    default -> throw new IllegalStateException("Unexpected value: " + discount.getType());
                }
            }
        }
    }
}