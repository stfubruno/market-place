package me.bruno.product;

import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.bruno.database.Mongo;
import me.bruno.utils.CodeGenerator;
import org.bson.Document;

import java.util.*;

import static me.bruno.Main.Logger;

public class ProductHandler {

    @Getter
    private static final Map<Long, Product> products = new HashMap<>();

    private static MongoCollection<Document> collection;

    public static void initialize() {
        collection = Mongo.getMongoDatabase().getCollection("products", Document.class);
    }

    public void load(String ownerName) {

    }

    public static void registerProduct(String name, double price, int units, boolean ageRestriction) {
        long twelveDigitLong = CodeGenerator.create12DigitLong();

        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            Product product = entry.getValue();
            if (product.name.equals(name)) {
                System.out.print(Logger + "A product with name " + product.name + " already exists.");
                return;
            }

            if (product.code == twelveDigitLong) {
                twelveDigitLong = CodeGenerator.create12DigitLong();
            }
        }

        if (products.containsKey(twelveDigitLong)) {
            twelveDigitLong = CodeGenerator.create12DigitLong();
        }

        Product product = new Product(name, price, twelveDigitLong, units, ageRestriction);

        products.put(twelveDigitLong, product);
        System.out.println(Logger + "Successfully registered " + units + " units of '" + product.name + "' (Code: " + product.code + ").");
    }

    public static Product findById(long code) {
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            Product product = entry.getValue();
            if (Objects.equals(product.code, code)) {
                return product;
            }
        }

        return null;
    }

    public static Product findByName(String name) {
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            Product product = entry.getValue();
            if (Objects.equals(product.name, name)) {
                return product;
            }
        }

        return null;
    }

    //to be removed.
    public static void manageProduct(Product productToManage) {
        for (Map.Entry<Long, Product> entry : products.entrySet()) {
            Product product = entry.getValue();

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

                        productToManage.setUnits(userAmountChoice);
                        break;
                    case 9:
                    default:
                        System.out.println("Invalid option. Please select a valid menu item.");
                }
            }
        }
    }
}