package me.bruno.discount;

import me.bruno.basket.Basket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DiscountHandler {
    public static List<Discount> discounts = new ArrayList<>();

    public static void createDiscount(String name, DiscountType type, int value) {
        UUID uuid = UUID.randomUUID();

        //duplicate prevention
        for (Discount discount : discounts) {
            if (discount.getUuid() == uuid) {
                uuid = UUID.randomUUID();
            }
        }

        Discount discount = new Discount(name, uuid, value, DiscountStatus.ACTIVE, type);
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

    public static Discount findByName(String name) {
        for (Discount discount : discounts) {
            if (Objects.equals(discount.getName(), name)) {
                return discount;
            }
        }

        return null;
    }

    public static void applyDiscount(Basket basket, Discount discount) {
        if (discount.getStatus() != DiscountStatus.ACTIVE) {
            return; // Exit if the discount is not active
        }

        for (Discount discountsArray : discounts) {
            if (discountsArray.getUuid() == discount.getUuid()) {
                switch (discount.getType()) {
                    case DiscountType.PERCENTAGE -> {
                        basket.decrementValue(basket.getValue() * discount.decimalValue(discount.getValue()));
                        basket.setDiscountApplied(discount);
                    }

                    case DiscountType.VALUE -> {
                        basket.decrementValue(discount.getValue());
                        basket.setDiscountApplied(discount);
                    }

                    default -> throw new IllegalStateException("Unexpected value: " + discount.getType());
                }
            }
        }
    }
}