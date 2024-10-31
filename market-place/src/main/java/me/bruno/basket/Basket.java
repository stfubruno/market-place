package me.bruno.basket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.bruno.Main;
import me.bruno.product.Product;
import me.bruno.user.User;

import java.util.*;

@Getter
@AllArgsConstructor
public class Basket {

    User owner;
    UUID uuid;
    @Setter
    List<Product> products;
    @Setter
    double value;
    @Setter
    Enum<BasketStatus> status;

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
        this.value -= value;
    }

    public void reset() {
        products.clear();
        value = 0;
        status = BasketStatus.ABANDONED; //necessary??
    }
}