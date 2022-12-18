package com.tickettranslate.translaterequest;


// import org.bson.json.JsonObject;
//import org.springframework.boot.json.JsonParser;
import com.tickettranslate.core.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import com.atlassian.jira.rest.client;
import java.util.Base64;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

@Service
public class TranslateRequestService {

    @Value("${Translation.Source.URL}")
    private String translationSourceURL;

    @Value("${Translation.Source.Credentials}")
    private String translationSourceCredentials;

    private TranslatorService translatorService;

    @Autowired
    public TranslateRequestService(TranslatorService translatorService)
    {
        this.translatorService = translatorService;
    }
    public boolean translate(String sourceID, String ticketID)
    {
        String json = getTicket(sourceID, ticketID);
        String description = paresTicketDescription(json);
        String translation = translatorService.translate(description, "en", "de");
        updateTicketDescription(sourceID, ticketID, translation);

        return true;
    }


    private String paresTicketDescription(String ticketJson)
    {
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(ticketJson);
        JsonObject o = rootNode.getAsJsonObject();
        String key = o.get("key").getAsString();
        String self = o.get("self").getAsString();
        JsonObject fields = o.get("fields").getAsJsonObject();
        String description = fields.get("description").getAsString();

        return description;
    }


    private void updateTicketDescription(String sourceID, String ticketID, String description)
    {
        String json = null;
        //JSONObject object;
        //description="Hello";
        try {
            // request url
            String url = translationSourceURL + ticketID;

            // create auth credentials
            String base64Creds = Base64.getEncoder().encodeToString(translationSourceCredentials.getBytes());

            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + base64Creds);
            headers.add("Content-type", "application/json");

            // create body
            //String body = "{ \"update\" : { \"components\" : [{ \"set\" : [{ \"description\" : \"" + description + "\" }]}]}}";
            String body = "{ \"fields\" : { \"description\" : \"" + description + "\" }}";

            // create request
            HttpEntity request = new HttpEntity(body, headers);

            // make a request
            ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.PUT, request, String.class);

            // get JSON response
            json = response.getBody();



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return;
    }



    private String getTicket(String sourceID, String ticketID)
    {
        String json = null;
        //JSONObject object;

        try {
            // request url
            String url = translationSourceURL + ticketID;

            // create auth credentials
            String base64Creds = Base64.getEncoder().encodeToString(translationSourceCredentials.getBytes());

            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + base64Creds);

            // create request
            HttpEntity request = new HttpEntity(headers);

            // make a request
            ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

            // get JSON response
            json = response.getBody();



        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return json;
    }
}



