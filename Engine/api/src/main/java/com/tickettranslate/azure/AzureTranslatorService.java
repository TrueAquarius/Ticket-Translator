package com.tickettranslate.azure;

import com.tickettranslate.core.TranslatorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import com.atlassian.jira.rest.client;
import java.net.URI;
import java.util.Base64;

import com.google.gson.*;




@Service
public class AzureTranslatorService implements TranslatorService {

    @Value("${Translation.Azure.URL}")
    private String baseUrl;

    @Value("${Translation.Azure.Location}")
    private String location;

    @Value("${Translation.Azure.Key}")
    private String key;

    @Override
    public String translate(String textToTranslate, String fromLanguage, String toLanguage) {

        // This is where we return the translated text
        String translatedText = "";

        // Create Body
        RequestBody body = new RequestBody(textToTranslate);
        Gson gson = new Gson();
        String bodyString = "[" + gson.toJson(body) + "]";

        // Create URL
        String route = "/translate?api-version=3.0&from=" + fromLanguage + "&to=" + toLanguage;
        String url = (baseUrl + route);

        // Create MediaType
        MediaType mediaType = MediaType.parseMediaType("application/json");

        // Create Headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Ocp-Apim-Subscription-Key", key);
        headers.add("Ocp-Apim-Subscription-Region", location);
        headers.add("Content-type", "application/json");

        try {
            // Create Request
            HttpEntity request = new HttpEntity(bodyString, headers);


            // Call Azure Translate
            ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.POST, request, String.class);
            String answerJson = response.getBody();

            JsonParser parser = new JsonParser();
            JsonElement rootNode = parser.parse(answerJson);
            JsonArray translations = rootNode.getAsJsonArray();
            JsonObject languageTranslations = translations.get(0).getAsJsonObject();
            JsonArray o = languageTranslations.getAsJsonArray("translations");
            JsonObject p = o.get(0).getAsJsonObject();
            translatedText = p.get("text").getAsString();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return translatedText;
    }
}
