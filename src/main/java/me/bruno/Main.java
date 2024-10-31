package me.bruno;

import lombok.Getter;
import me.bruno.product.Product;
import me.bruno.product.ProductHandler;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    @Getter
    public static final String adminAccess = "v5!X1%A0%z0&p5"; //Might be changed

    public static void main(String[] args) {
        System.out.print("Hello and welcome!\n");

        ProductHandler.registerProduct("Coca Cola", 5.5, 100, false);
        ProductHandler.registerProduct("Pepsi", 5.2, 1000, false);

        Scanner choice = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("\n1 -> Find product by ID\n");
            System.out.print("2 -> Find product by Name\n");
            System.out.print("3 -> Manage Product\n");
            System.out.print("4 -> Register Sale\n");
            System.out.print("9 -> Exit\n");
            System.out.print("Enter your choice: ");

            int userChoice;

            while (true) {
                try {
                    userChoice = choice.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer.");
                    choice.next();
                }
            }

            switch (userChoice) {
                case 1:
                    System.out.print("Enter product ID: ");
                    Scanner longInput = new Scanner(System.in);
                    long longInputId = longInput.nextLong();

                    Product productByID = ProductHandler.findById(longInputId);

                    if (productByID == null) {
                        System.out.println("Product not found.");
                        return;
                    }

                    System.out.print("\nName >> " + productByID.getName());
                    System.out.print("\nPrice >> " + productByID.getPrice());
                    System.out.print("\nQuantity >> " + productByID.getAmount() + "\n");
                    break;

                case 2:
                    System.out.print("Enter product Name: ");
                    Scanner stringInput = new Scanner(System.in);
                    String stringInputName = stringInput.nextLine();

                    Product productByName = ProductHandler.findByName(stringInputName);

                    if (productByName == null) {
                        System.out.println("Product not found.");
                        return;
                    }

                    System.out.print("\nName >> " + productByName.getName());
                    System.out.print("\nPrice >> " + productByName.getPrice());
                    System.out.print("\nAmount >> " + productByName.getAmount() + "\n");

                    break;

                case 3:
                    System.out.print("Enter product ID: ");
                    Scanner manageLongInput = new Scanner(System.in);
                    long manageLongInputId = manageLongInput.nextLong();

                    Product manageProductID = ProductHandler.findById(manageLongInputId);

                    if (manageProductID == null) {
                        System.out.println("Product not found.");
                        return;
                    }

                    ProductHandler.manageProduct(manageProductID);
                    break;
                case 9:
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please select a valid menu item.");
            }
        }
    }
}