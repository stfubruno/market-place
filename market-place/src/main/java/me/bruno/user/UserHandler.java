package me.bruno.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.bruno.database.Mongo;
import org.bson.Document;

import java.util.*;

import static me.bruno.Main.*;

public class UserHandler {

    //TODO: find user by name, phone, email.

    @Getter
    private static final Map<UUID, User> users = new HashMap<>();

    private static MongoCollection<Document> collection;

    public static void initialize() {
        collection = Mongo.getMongoDatabase().getCollection("users", Document.class);
    }

    public static void createUser(String name, int age, String email, String phone, boolean debug) {
        UUID uuid = UUID.randomUUID();

        User user = new User(uuid, name, age, email, phone);

        users.put(uuid, user);
        System.out.println(Logger + "Successfully registered user: " + name);

        Document document = new Document();

        document.put("id", user.getId().toString());
        document.put("name", user.getName());
        document.put("age", user.getAge());
        document.put("email", user.getEmail());
        document.put("phone", user.getPhone());

        // Convert document to JSON and pretty print it
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonStringUser = gson.toJson(document);

        System.out.println(ANSI_WHITE + "User Information:\n" + jsonStringUser + ANSI_RESET);
    }

    public static User findUser(String name) {
        for (Map.Entry<UUID, User> entry : users.entrySet()) {
            User user = entry.getValue();

            if (Objects.equals(name, user.getName())) {
                return user;
            }
        }

        return null;
    }

}