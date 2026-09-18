package org.example.me.Plugin;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Client {
    HttpClient client;
    String url = "http://localhost:3000/build";

    public Client(){
        this.client = connect();
    }

    private HttpClient connect(){
        try {
            System.out.println("Connecting to server...");
            return HttpClient.newHttpClient();

        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    //Gebe das JSON vom Endpoint (url) zurück
    public JsonObject startGetRequest(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());


            return JsonParser.parseString(response.body()).getAsJsonObject();

        }catch (Exception e){
            throw new RuntimeException();

        }

    }
}
