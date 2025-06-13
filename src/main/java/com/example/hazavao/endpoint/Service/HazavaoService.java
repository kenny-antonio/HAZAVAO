package com.example.hazavao.endpoint.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class HazavaoService {
    private final String apiKey = "sk...kadjajfaifajiojgiugui";
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "https://api.openai.com/v1";

    public ResponseEntity<Map<String, String>> getDefinition(String teny) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{\"model\":\"text-davinci-003\",\"prompt\":\"Définir le mot: " + teny + "\",\"max_tokens\":100}";
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            String response = restTemplate.postForObject(baseUrl + "/completions", entity, String.class);

            String definition = extractDefinitionWithRegex(response);

            return ResponseEntity.ok(Map.of("mot", teny.toLowerCase(), "definition", definition));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erreur lors de l’appel à l’API OpenAI"));
        }
    }

    private String extractDefinitionWithRegex(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode choicesNode = rootNode.path("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                return choicesNode.get(0).path("text").asText().trim();
            }
            return "Définition introuvable";
        } catch (Exception e) {
            return "Erreur lors de l'extraction de la définition";
        }
    }
}