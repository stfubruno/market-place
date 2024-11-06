package me.bruno.discount;

import lombok.Getter;

import java.util.*;

import static me.bruno.Main.Logger;

public class DiscountHandler {

    @Getter
    private static final Map<UUID, Discount> discounts = new HashMap<>();

    public static void createDiscount(String name, int value, String type) {
        UUID uuid = UUID.randomUUID();

        Discount discount = new Discount(uuid, name, value, type);
        discounts.put(uuid, discount);
        System.out.println(Logger + "Successfully registered discount: \"" + name + "\".");
    }

    public static void disableDiscount(Discount discount) {
        for (Map.Entry<UUID, Discount> entry : DiscountHandler.getDiscounts().entrySet()) {
            Discount discountEntry = entry.getValue();

            if (!discountEntry.getUuid().equals(discount.getUuid()) || discount.getStatus() != DiscountStatus.ACTIVE) return;

            discount.status = DiscountStatus.INACTIVE;
            System.out.println(Logger + "Successfully inactivated discount: \"" + discount.getName() + "\".");
        }
    }

    public static void enableDiscount(Discount discount) {
        for (Map.Entry<UUID, Discount> entry : DiscountHandler.getDiscounts().entrySet()) {
            Discount discountEntry = entry.getValue();

            if (!discountEntry.getUuid().equals(discount.getUuid()) || discount.getStatus() != DiscountStatus.INACTIVE) return;

            discount.status = DiscountStatus.ACTIVE;
            System.out.println(Logger + "Successfully activated discount: \"" + discount.getName() + "\".");
        }
    }

    public static Discount findByName(String name) {
        for (Map.Entry<UUID, Discount> entry : DiscountHandler.getDiscounts().entrySet()) {
            Discount discount = entry.getValue();

            if (Objects.equals(discount.getName(), name)) return discount;
        }

        return null;
    }
}