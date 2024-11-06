package me.bruno.basket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.bruno.Main;
import me.bruno.discount.Discount;
import me.bruno.discount.DiscountHandler;
import me.bruno.discount.DiscountStatus;
import me.bruno.discount.DiscountType;
import me.bruno.product.Product;
import me.bruno.user.User;

import java.util.*;

import static me.bruno.Main.Logger;

@Getter
@AllArgsConstructor
public class Basket {

    UUID id;

    @Setter
    User owner;

    @Setter
    List<Product> products;

    @Setter
    double value;

    @Setter
    double discountAmount;

    @Setter
    Enum<BasketStatus> status;

    @Setter
    long createdAt;

    @Setter
    Discount discountApplied;

    public Basket(UUID id, User owner) {
        this(id, owner, new ArrayList<>(), 0.0D, 0.0D, BasketStatus.ACTIVE, System.currentTimeMillis(), null);
    }

    public Basket(UUID id, User owner, List<Product> products, double value) {
        this(id, owner, products, value, 0.0D, BasketStatus.ACTIVE, System.currentTimeMillis(), null);
    }

    public String getDiscountApplied() {
        if (discountApplied == null) {
            return "None";
        }

        return discountApplied.getName();
    }

    public void changeBasketOwner(User owner) {
        Scanner choice = new Scanner(System.in);
        String adminAccess;

        System.out.print("Please enter the admin access code: ");

        while (true) {
            try {
                adminAccess = choice.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                choice.next();
            }
        }

        if (!Objects.equals(adminAccess, Main.adminAccess)) {
            System.out.println("Incorrect admin access code entered. Please try again.");
            return;
        }

        this.owner = owner;
    }

    public void incrementValue(double value) {
        this.value += value;
    }

    public void decrementValue(double value) {
        if (value <= 0) return;

        //Ensure value does not go below 0
        this.value = Math.max(0, this.value - value);
    }

    public void reset() {
        products.clear();
        value = 0;
        status = BasketStatus.ABANDONED; //necessary??
    }

    public void addToBasket(Product product) {
        products.add(product);
        incrementValue(product.getPrice());
        product.decrementUnits(1);
        System.out.println(Logger + "Successfully added \"" + product.getName() + "\" to basket with ID: " + id + ".");
    }

    public void addToBasket(Product product, int units) {
        if (units <= 0) return;

        if (product.getUnits() == 0) {
            System.out.println(Logger + "Requested quantity of " + units + " for " + product.getName() + " is out of stock. No units available.");
            return;
        }

        if (units > product.getUnits()) {
            System.out.println(Logger + "Requested quantity of " + units + " for " + product.getName() + " is out of stock. Only " + product.getUnits() + " units available in stock.");
            return;
        }

        for (int i = 0; i < units; i++) {
            products.add(product);
        }

        incrementValue(product.getPrice() * units);
        product.decrementUnits(units);
        System.out.println(Logger + "Successfully added " + units + " of \"" + product.getName() + "\" to basket with ID: " + id + ".");
    }

    public void removeFromBasket(Product product) {
        if (!products.contains(product)) {
            System.out.println(Logger + "Product does not match any product.");
            return;
        }

        products.remove(product);
        decrementValue(product.getPrice());
        product.incrementUnits(1);
        System.out.println(Logger + "Successfully removed \"" + product.getName() + "\" from basket with ID: " + id + ".");
    }

    public void removeFromBasket(Product product, int units) {
        if (units <= 0) return;

        if (!products.contains(product)) {
            System.out.println(Logger + "Product does not match any product.");
            return;
        }

        for (int i = 0; i < units; i++) {
            products.remove(product);
        }

        decrementValue(product.getPrice() * units);
        product.incrementUnits(units);
        System.out.println(Logger + "Successfully removed " + units + " of \"" + product.getName() + "\" from basket with ID: " + id + ".");
    }

    public void applyDiscount(Discount discount) {
        if (discount.getStatus() != DiscountStatus.ACTIVE) {
            return; // Exit if the discount is not active
        }

        for (Map.Entry<UUID, Discount> entry : DiscountHandler.getDiscounts().entrySet()) {
            Discount discountEntry = entry.getValue();

            if (discountEntry.getUuid() != discount.getUuid()) return;

            switch (discount.getType()) {
                case DiscountType.PERCENTAGE -> {
                    double percent = discount.getValue() / 100.0;
                    double finalValue = value * percent;

                    decrementValue(finalValue);
                    discountAmount = finalValue;
                    discountApplied = discount;
                    System.out.println(Logger + "Successfully applied the discount \"" + discount.getName() + "\" of " + (int) discount.getValue() + "% to basket with ID: " + id + ".");
                }

                case DiscountType.VALUE -> {
                    decrementValue(discount.getValue());
                    discountAmount = discount.getValue();
                    discountApplied = discount;
                    System.out.println(Logger + "Successfully applied the discount \"" + discount.getName() + "\" of " + String.format("%.2f", discount.getValue()) + " to basket with ID: " + id + ".");
                }

                default -> throw new IllegalStateException("Unexpected value: " + discount.getType());
            }
        }
    }

}