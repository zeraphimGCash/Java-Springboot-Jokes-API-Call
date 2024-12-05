package com.JCDiamante.SpringAPICall.SpringAPICall;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SuperHeroAPI {

    private static final String BASE_URL = "https://superheroapi.com/api/";
    private static Dotenv dotenv;

    static {
        try {
            // Load .env file
            dotenv = Dotenv.configure().load();
        } catch (Exception e) {
            System.err.println("Error loading .env file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static final String ACCESS_TOKEN = dotenv.get("ACCESS_TOKEN");

    public static void main(String[] args) {

        int characterId = 5;

        // Fetch character general information
        JsonObject characterInfo = fetchCharacterData(characterId);
        if (characterInfo != null) {
            printCharacterInfo(characterInfo, characterId);

            System.out.println("\nOriginal JSON response:\n" + characterInfo);
        }

        // Fetch character powerstats
        JsonObject powerStats = fetchPowerStats(characterId);
        if (powerStats != null) {
            printPowerStats(powerStats, characterId);

            System.out.println("\nOriginal JSON response:\n" + powerStats);
        }
    }

    // Fetch character data by ID
    private static JsonObject fetchCharacterData(int characterId) {
        String endpoint = BASE_URL + ACCESS_TOKEN + "/" + characterId;
        return makeApiCall(endpoint);
    }

    // Fetch character power stats by ID
    private static JsonObject fetchPowerStats(int characterId) {
        String endpoint = BASE_URL + ACCESS_TOKEN + "/" + characterId + "/powerstats";
        return makeApiCall(endpoint);
    }

    // Generic method to make an API call and parse the JSON response
    private static JsonObject makeApiCall(String endpoint) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String json = restTemplate.getForObject(endpoint, String.class);

            // Parse JSON response
            return JsonParser.parseString(json).getAsJsonObject();
        } catch (Exception e) {
            System.err.println("Error fetching data from endpoint: " + endpoint);
            e.printStackTrace();
            return null;
        }
    }

    // Print character information in a readable format
    private static void printCharacterInfo(JsonObject characterInfo, int characterID) {
        System.out.println("\n============ Character Info (Character #" + String.valueOf(characterID) + ") ============\n");
        System.out.println("Name: " + characterInfo.get("name").getAsString());
        JsonObject biography = characterInfo.getAsJsonObject("biography");
        System.out.println("Full Name: " + biography.get("full-name").getAsString());
        System.out.println("Place of Birth: " + biography.get("place-of-birth").getAsString());
        System.out.println("First Appearance: " + biography.get("first-appearance").getAsString());
        System.out.println("Publisher: " + biography.get("publisher").getAsString());
        System.out.println("Alignment: " + biography.get("alignment").getAsString());
    }

    // Print power stats in a readable format
    private static void printPowerStats(JsonObject powerStats, int characterID) {
        System.out.println("\n============ Character PowerStats: (Character #" + String.valueOf(characterID) + ") ============\n");
        System.out.println("Intelligence: " + powerStats.get("intelligence").getAsString());
        System.out.println("Strength: " + powerStats.get("strength").getAsString());
        System.out.println("Speed: " + powerStats.get("speed").getAsString());
        System.out.println("Durability: " + powerStats.get("durability").getAsString());
        System.out.println("Power: " + powerStats.get("power").getAsString());
        System.out.println("Combat: " + powerStats.get("combat").getAsString());
    }
}
