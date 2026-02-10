package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ChatController {

    @Value("${app.gemini.api-key}")
    private String apiKey;

    @Value("${app.gemini.url}")
    private String apiUrl;

    @Value("${app.gemini.temperature:0.7}")
    private double temperature;

    private final RestTemplate rest = new RestTemplate();

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String userMsg = request.getOrDefault("message", "");
        Map<String, String> response = new HashMap<>();

        if (userMsg == null || userMsg.trim().isEmpty()) {
            response.put("reply", "Pakiusap po, maglagay ng inyong mensahe.");
            return response;
        }

        if (userMsg.toLowerCase().matches(".*\\b(agent|kausap|tawag|human|tulong|help|contact|numero|email)\\b.*")) {
            response.put("reply", "Maaari niyo pong tawagan ang Sparta Credit sa (02) 8249-9200 o mag-email sa partnership@spartacollects.com.");
            return response;
        }

        response.put("reply", getGeminiResponse(userMsg));
        return response;
    }

    private String getGeminiResponse(String prompt) {
        try {
            String finalUrl = apiUrl.trim() + ":generateContent?key=" + apiKey.trim();

            Map<String, Object> body = new HashMap<>();
            
            Map<String, Object> textPart = new HashMap<>();
     
textPart.put("text", 
    "Role: Official AI Assistant of Sparta Credit Management Services Inc. (SCMSI). " +
    "Motto: 'We Trace, We Collect, We Recover.' " +
    "Company Details: Located at Unit 802, Citystate Centre Bldg., 709 Shaw Blvd., Pasig City. " +
    "Policy: Use 'po' and 'opo' always. Strictly answer ONLY questions related to Sparta Credit, " +
    "debt collection, payments, and skip tracing. If the user asks about unrelated topics " +
    "(e.g., entertainment, food, or other companies), politely say: " +
    "'Paumanhin po, ang aking kaalaman ay limitado lamang sa mga serbisyo ng Sparta Credit.'" +
    "\nUser Question: " + prompt
);
            Map<String, Object> contentMap = new HashMap<>();
            contentMap.put("parts", List.of(textPart));
            body.put("contents", List.of(contentMap));

            Map<String, Object> config = new HashMap<>();
            config.put("temperature", temperature);
            body.put("generationConfig", config);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);


            ResponseEntity<Map<String, Object>> exchange = rest.exchange(
          finalUrl,
            HttpMethod.POST, 
        entity,
    new ParameterizedTypeReference<Map<String, Object>>() {}
);

            Map<String, Object> responseBody = exchange.getBody();

            if (responseBody != null && responseBody.containsKey("candidates")) {
                List<?> candidates = (List<?>) responseBody.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
                    Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
                    if (content != null) {
                        List<?> parts = (List<?>) content.get("parts");
                        if (!parts.isEmpty()) {
                            Map<?, ?> firstPart = (Map<?, ?>) parts.get(0);
                            return firstPart.get("text").toString();
                        }
                    }
                }
            }
            
            return "Pasensya na po, hindi ko mahanap ang tamang sagot. Maaari niyo kaming tawagan sa (02) 8249-9200.";

        } catch (org.springframework.web.client.HttpClientErrorException e) {
            System.err.println("--- GOOGLE API ERROR LOG ---");
            System.err.println("Status: " + e.getStatusCode());
            System.err.println("Body: " + e.getResponseBodyAsString());
            return "Paumanhin, may error sa connection sa AI (" + e.getStatusCode() + ").";
        } catch (Exception e) {
            e.printStackTrace();
            return "Nagkaroon po ng technical error. Pakisubukan muli mamaya.";
        }
    }
}