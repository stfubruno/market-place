package me.bruno.database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.bruno.Main;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Mongo {

    @Getter
    public static MongoClient mongoClient;

    @Getter
    public MongoDatabase mongoDatabase;

    public Mongo() {
        try {
            // Load JSON configuration from resources
            InputStream input = Main.class.getClassLoader().getResourceAsStream("config.json");

            if (input == null) {
                throw new RuntimeException("config.json not found in resources");
            }

            // Parse JSON without using Config class
            ObjectMapper mapper = new ObjectMapper();
            JsonNode configNode = mapper.readTree(input);

            // Access fields from JSON
            String host = configNode.get("host").asText();
            int port = configNode.get("port").asInt();
            String database = configNode.get("database").asText();
            // URL-encode username and password
            String username = URLEncoder.encode(configNode.get("username").asText(), StandardCharsets.UTF_8);
            String password = URLEncoder.encode(configNode.get("password").asText(), StandardCharsets.UTF_8);

            String uri = "mongodb://" + username + ":" + password + "@" + host + ":" + port;

            mongoClient = MongoClients.create(uri);
            mongoDatabase = mongoClient.getDatabase(database);
        } catch (Exception e) {
            System.out.println("Error loading config: " + e.getMessage());
        }
    }

}