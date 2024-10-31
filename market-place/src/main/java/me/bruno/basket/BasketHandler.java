package me.bruno.basket;

import me.bruno.product.Product;
import me.bruno.user.User;

import java.util.*;

public class BasketHandler {
    public static List<Basket> baskets = new ArrayList<>();

    public static void createBasket(User user) {
        List<Product> empty = new ArrayList<>();
        UUID uuid = UUID.randomUUID();

        //duplicate prevention
        for (Basket basket : baskets) {
            if (basket.getUuid() == uuid) {
                uuid = UUID.randomUUID();
            }
        }

        Basket basket = new Basket(user, uuid, empty, BasketStatus.ACTIVE);
        baskets.add(basket);
        System.out.println("Successfully registered basket >> " + uuid + " <<");
    }

    public static void createBasket(User user, Product product) {
        List<Product> empty = new ArrayList<>(Collections.singletonList(product));
        UUID uuid = UUID.randomUUID();

        //duplicate prevention
        for (Basket basket : baskets) {
            if (basket.getUuid() == uuid) {
                uuid = UUID.randomUUID();
            }
        }

        Basket basket = new Basket(user, uuid, empty, BasketStatus.ACTIVE);
        baskets.add(basket);
        System.out.println("Successfully registered basket >> " + uuid + " <<");
    }

    public static void addToBasket(Basket basketToUpdate, Product product) {
        for (Basket basket : baskets) {
            if (Objects.equals(basket.getUuid(), basket.getUuid())) {
                basket.getProducts().add(product);
            }
        }
    }
}