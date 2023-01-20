package org.vaadin.example;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class GreetService implements Serializable {

    private static final String api = "http://localhost:8081/%s";
    private static final String apiId = "http://localhost:8081/%s/%s";

    HttpRequest request;
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<String> response;

    public String getIP(String type) {
        try {
            //Se crea un objeto HttpRequest con la url de la API
            String resource = String.format(api, type, "");
            //Se crea un objeto HttpRequest con la url de la API
            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            //Se envía la petición a la API
            response = client.send(request, HttpResponse.BodyHandlers.ofString());


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public String getIPId(String type, int id) {
        try {
            //Se crea un objeto HttpRequest con la url de la API
            String resource = String.format(api, type, id);
            //Se crea un objeto HttpRequest con la url de la API
            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            //Se envía la petición a la API
            response = client.send(request, HttpResponse.BodyHandlers.ofString());


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public String postIP(String type, String ip_from, String ip_to, String country_code, String country_name, String region_name, String city_name, String latitude, String longitude, String zip_code, String time_zone) {
        try {
            String resource = String.format(api, type);

            var valoresIP = new HashMap<String, String>() {{
                put("ip_from", ip_from);
                put("ip_to", ip_to);
                put("country_code", country_code);
                put("country_name", country_name );
                put("region_name", region_name);
                put("city_name", city_name);
                put("latitude", latitude);
                put("longitude", longitude);
                put("zip_code", zip_code);
                put("time_zone", time_zone);
            }};

            var objectMapper = new ObjectMapper();
            String IP = objectMapper.writeValueAsString(valoresIP);

            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(IP))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public String putIP(String type, int id, String ip_from, String ip_to, String country_code, String country_name, String region_name, String city_name, String latitude, String longitude, String zip_code, String time_zone) {
        try {
            String resource = String.format(apiId, type, id);

            var valoresIP = new HashMap<String, String>() {{
                put("ip_from", ip_from);
                put("ip_to", ip_to);
                put("country_code", country_code);
                put("country_name", country_name );
                put("region_name", region_name);
                put("city_name", city_name);
                put("latitude", latitude);
                put("longitude", longitude);
                put("zip_code", zip_code);
                put("time_zone", time_zone);
            }};

            var objectMapper = new ObjectMapper();
            String IP = objectMapper.writeValueAsString(valoresIP);

            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(IP))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public String deleteIP(String type, int id) {
        try {
            String resource = String.format(apiId, type, id);

            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }
}
