package me.bruno;

import lombok.Getter;
import me.bruno.basket.Basket;
import me.bruno.basket.BasketHandler;
import me.bruno.database.Mongo;
import me.bruno.discount.Discount;
import me.bruno.discount.DiscountHandler;
import me.bruno.discount.DiscountType;
import me.bruno.product.Product;
import me.bruno.product.ProductHandler;
import me.bruno.user.User;
import me.bruno.utils.TimeUtil;

import java.util.Objects;

public class Main {

    @Getter
    public static final String adminAccess = "v5!X1%A0%z0&p5";

    public static void main(String[] args) {
        System.out.print("Hello and welcome!\n");

        new Mongo();

        //VV Tests VV
        User user = new User("Bruno", 533638293, "market-place@gmail.com", "Bruno@market-place");

        ProductHandler.registerProduct("Coca-Cola", 5.5, 5000, false);
        Product product = Objects.requireNonNull(ProductHandler.findByName("Coca-Cola"));

        BasketHandler.createBasket(user, product, 5);
        Basket basket = Objects.requireNonNull(BasketHandler.findByOwner(user));

        DiscountHandler.createDiscount("Nov30", DiscountType.VALUE, 50);

        //Before discount
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("ID: " + basket.getUuid());
        System.out.println("Owner: " + basket.getOwner().getName());
        System.out.println("Products: " + basket.getProducts().size());
        System.out.println("Discount: " + basket.getDiscountApplied());
        System.out.println("Value: " + basket.getValue());
        System.out.println("Status: " + basket.getStatus());
        System.out.println("Created: " + TimeUtil.millisToRoundedTime(System.currentTimeMillis() - basket.getCreatedAt()) + " ago");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

        Discount discount = Objects.requireNonNull(DiscountHandler.findByName("Nov30"));
        DiscountHandler.applyDiscount(basket, discount);

        //After discount of 30%
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("ID: " + basket.getUuid());
        System.out.println("Owner: " + basket.getOwner().getName());
        System.out.println("Products: " + basket.getProducts().size());
        System.out.println("Discount: " + basket.getDiscountApplied());
        System.out.println("Value: " + basket.getValue());
        System.out.println("Status: " + basket.getStatus());
        System.out.println("Created: " + TimeUtil.millisToRoundedTime(System.currentTimeMillis() - basket.getCreatedAt()) + " ago");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
    }
}