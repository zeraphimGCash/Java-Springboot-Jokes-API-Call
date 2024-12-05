package com.JCDiamante.SpringAPICall.SpringAPICall;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RandomTen {

    public static void main(String[] args) {
        // Fetch jokes from the /random_ten endpoint
        String json = new RestTemplate().getForObject("https://official-joke-api.appspot.com/random_ten", String.class);

        JsonArray jokes = JsonParser.parseString(json).getAsJsonArray();

        // Exception handling to check if there are at least 5 jokes in the API call
        if (jokes.size() >= 5) {
            // Get the 5th object in the JSON response (index 4)
            JsonObject fifthJoke = jokes.get(4).getAsJsonObject();

            // Get the setup string for the joke
            String setup = fifthJoke.get("setup").getAsString();

            // Get the punchline string for the joke
            String punchline = fifthJoke.get("punchline").getAsString();

            // Output
            System.out.println("The fifth joke is:");
            System.out.println(setup + ", " + punchline);

        } else {
            System.out.println("Not enough jokes were returned from the API.");
        }

        // Print all the jokes fetched (proof that the fifth joke is fetched in the first output)
        System.out.println("\nTen jokes fetched (proof that the fifth joke is fetched in the first output)");
        for (int i = 0; i < jokes.size(); i++) {
            JsonObject joke = jokes.get(i).getAsJsonObject();
            String setup = joke.get("setup").getAsString();
            String punchline = joke.get("punchline").getAsString();

            System.out.println((i + 1) + ". " + setup + " - " + punchline);
        }
    }
}