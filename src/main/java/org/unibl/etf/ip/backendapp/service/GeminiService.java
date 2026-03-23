package org.unibl.etf.ip.backendapp.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final Client client;

    public GeminiService(@Value("${gemini.api.key}") String apiKey) {
        this.client = Client.builder()
                .apiKey(apiKey)
                .build();
    }

    public String generateAnswer(String prompt) {
        try {
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.5-flash",
                            prompt,
                            null);

            return response.text();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while generating response: " + e.getMessage();
        }
    }

    public void provjeriModele() {
        try {
            client.models.list(null).forEach(m -> System.out.println("Available model: " + m.name()));
        } catch (Exception e) {
            System.out.println("Unable to show models: " + e.getMessage());
        }
    }
}