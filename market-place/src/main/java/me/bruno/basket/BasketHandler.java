package me.bruno.basket;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import me.bruno.database.Mongo;
import me.bruno.discount.Discount;
import me.bruno.discount.DiscountHandler;
import me.bruno.product.Product;
import me.bruno.product.ProductHandler;
import me.bruno.user.User;
import me.bruno.user.UserHandler;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import java.util.*;

import static me.bruno.Main.Logger;

public class BasketHandler {

    @Getter
    private static final Map<UUID, Basket> baskets = new HashMap<>();

    private static MongoCollection<Document> collection;

    public static void initialize() {
        collection = Mongo.getMongoDatabase().getCollection("baskets", Document.class);
    }

    public void load(String ownerName) {
        Document document = collection.find(Filters.eq("owner", ownerName)).first();

        // Check if the document exists
        if (document != null) {
            UUID uuid = UUID.fromString(document.getString("id"));
            User user = UserHandler.findUser(ownerName);

            List<String> products = document.getList("products", String.class);
            List<Product> productList = new ArrayList<>();

            for (String productName : products) {
                Product product = ProductHandler.findByName(productName);
                if (product != null) {
                    productList.add(product);
                } else {
                    System.err.println("Product not found: " + productName);
                }
            }

            double value = document.getDouble("value");
            double discountApplied = document.getDouble("discountApplied");
            BasketStatus status = BasketStatus.valueOf(document.getString("status"));
            long createdAt = document.getLong("createdAt");
            Discount discount = DiscountHandler.findByName(document.getString("discount"));

            // Create a new Basket instance and cache it
            Basket basket = new Basket(uuid, user, productList, value, discountApplied, status, createdAt, discount);
            baskets.put(uuid, basket);
        } else {
            System.err.println("No basket found for owner: " + ownerName);
        }
    }

    public static void save(Basket basket) {
        Document document = new Document();

        document.put("id", String.valueOf(basket.getId()));
        document.put("owner", basket.getOwner().getName());

        Map<String, Integer> productQuantities = new HashMap<>();

        for (Product product : basket.getProducts()) {
            productQuantities.put(product.getName(), productQuantities.getOrDefault(product.getName(), 0) + 1);
        }

        List<Document> products = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
            Document productDocument = new Document("name", entry.getKey()).append("units", entry.getValue());
            products.add(productDocument);
        }

        document.put("products", products);

        document.put("value", basket.getValue());
        document.put("status", String.valueOf(basket.getStatus()));
        document.put("createdAt", basket.getCreatedAt());
        document.put("discount", basket.getDiscountApplied());

//        // Convert document to JSON and pretty print it
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String jsonString = gson.toJson(document);
//
//        // Print the formatted JSON
//        System.out.println("Saving document to MongoDB:\n" + jsonString);

        try {
            collection.replaceOne(Filters.eq("id", basket.getId().toString()), document, new ReplaceOptions().upsert(true));
            System.out.println("Document saved successfully.");
        } catch (Exception e) {
            System.err.println("Error saving document: " + e.getMessage());
        }
    }

    public static void createBasket(User user) {
        UUID uuid = UUID.randomUUID();

        Basket basket = new Basket(uuid, user);

        baskets.put(uuid, basket);
        System.out.println(Logger + "Successfully registered basket with ID: " + uuid + ".");
    }

    public static void createBasket(User user, Product product) {
        UUID uuid = UUID.randomUUID();

        Basket basket = new Basket(uuid, user, Collections.singletonList(product), product.getPrice());

        baskets.put(uuid, basket);
        System.out.println(Logger + "Successfully registered basket with ID: " + uuid + ".");
    }

    public static void createBasket(User user, Product product, int quantity) {
        List<Product> products = new ArrayList<>();
        UUID uuid = UUID.randomUUID();

        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }

        Basket basket = new Basket(uuid, user, products, (product.getPrice() * quantity));

        baskets.put(uuid, basket);
        System.out.println(Logger + "Successfully registered basket with ID: " + uuid + ".");
    }

    public static Basket findByOwner(User owner) {
        for (Map.Entry<UUID, Basket> entry : baskets.entrySet()) {
            Basket basket = entry.getValue();
            User user = Objects.requireNonNull(basket.getOwner());

            if (Objects.equals(owner.getName(), user.getName())) {
                return basket;
            }
        }

        return null;
    }

}