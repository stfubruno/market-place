package me.bruno.product;

import me.bruno.utils.CodeGenerator;

import java.util.*;

public class ProductHandler {
    public static List<Product> products = new ArrayList<>();

    public static void registerProduct(String name, double price, int quantity, boolean ageRestriction) {
        long twelveDigitLong = CodeGenerator.create12DigitLong();

        //duplicate prevention
        for (Product product : products) {
            if (product.name.equals(name)) {
                System.out.print("A product with name " + product.name + " already exists.");
                return;
            }

            if (product.code == twelveDigitLong) {
                twelveDigitLong = CodeGenerator.create12DigitLong();
            }
        }

        Product product = new Product(name, price, twelveDigitLong, quantity, ageRestriction);
        products.add(product);
        System.out.println("Successfully registered " + quantity + " " + product.name + ". [" + product.code + "]");
    }

    public static Product findById(long code) {
        for (Product product : products) {
            if (product.code == code) {
                return product;
            }
        }

        return null;
    }

    public static Product findByName(String name) {
        for (Product product : products) {
            if (Objects.equals(name, product.name)) {
                return product;
            }
        }

        return null;
    }

    public static void manageProduct(Product productToManage) {
        for (Product product : products) {
            if (product == productToManage) {
                System.out.print("\n1 -> Manage Price\n");
                System.out.print("2 -> Manage Amount\n");
                System.out.print("9 -> Exit\n");
                System.out.print("Enter your choice: ");

                Scanner choice = new Scanner(System.in);

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
                        System.out.print("Provide a new price for " + productToManage.getName() + ": ");

                        Scanner priceChoice = new Scanner(System.in);

                        double userPriceChoice;

                        while (true) {
                            try {
                                userPriceChoice = priceChoice.nextDouble();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter an integer.");
                                priceChoice.next();
                            }
                        }

                        productToManage.setPrice(userPriceChoice);
                        break;
                    case 2:
                        System.out.print("Provide a new amount for " + productToManage.getName() + ": ");

                        Scanner amountChoice = new Scanner(System.in);

                        int userAmountChoice;

                        while (true) {
                            try {
                                userAmountChoice = amountChoice.nextInt();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter an integer.");
                                amountChoice.next();
                            }
                        }

                        productToManage.setAmount(userAmountChoice);
                        break;
                    case 9:
                    default:
                        System.out.println("Invalid option. Please select a valid menu item.");
                }
            }
        }
    }
}