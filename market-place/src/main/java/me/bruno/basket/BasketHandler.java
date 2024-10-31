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

        Basket basket = new Basket(user, uuid, empty, 0, BasketStatus.ACTIVE);
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

        Basket basket = new Basket(user, uuid, empty, product.getPrice(), BasketStatus.ACTIVE);
        baskets.add(basket);
        System.out.println("Successfully registered basket >> " + uuid + " <<");
    }

    public static Basket findByOwner(User owner) {
        for (Basket basket : baskets) {
            if (basket.getOwner() == owner) {
                return basket;
            }
        }

        return null;
    }

    public static void addToBasket(Basket basketToUpdate, Product product) {
        for (Basket basket : baskets) {
            if (Objects.equals(basket.getUuid(), basketToUpdate.getUuid())) {
                System.out.println("Successfully added " + product.getName() + " to basket >> " + basketToUpdate.getUuid() + " <<");
                basket.incrementValue(product.getPrice());
                basket.getProducts().add(product);
            }
        }
    }

    public static void removeFromBasket(Basket basketToUpdate, Product product) {
        for (Basket basket : baskets) {
            if (Objects.equals(basket.getUuid(), basketToUpdate.getUuid())) {
                System.out.println("Successfully removed " + product.getName() + " to basket >> " + basketToUpdate.getUuid() + " <<");
                basket.decrementValue(product.getPrice());
                basket.getProducts().remove(product);
            }
        }
    }

}