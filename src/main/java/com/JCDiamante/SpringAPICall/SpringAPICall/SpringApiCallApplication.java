package com.JCDiamante.SpringAPICall.SpringAPICall;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringApiCallApplication {

	public static void main(String[] args) {
		String json = new RestTemplate().getForObject("https://official-joke-api.appspot.com/jokes/random", String.class);

		JsonObject response = JsonParser.parseString(json).getAsJsonObject();

		String a = response.get("setup").getAsString();
		String b = response.get("punchline").getAsString();

		System.out.println(a + ", " + b);
	}
}
