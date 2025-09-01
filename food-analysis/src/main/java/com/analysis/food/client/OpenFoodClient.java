package com.analysis.food.client;

import com.analysis.food.model.ProductIngredients;
import com.analysis.food.model.SearchProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Component
public class OpenFoodClient {

    private final String openfoodHost;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OpenFoodClient(@Value("${openfood.api.host}") String openfoodHost) {
        this.openfoodHost = (openfoodHost != null && openfoodHost.endsWith("/"))
                ? openfoodHost.substring(0, openfoodHost.length() - 1)
                : openfoodHost;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public ProductIngredients getProductByBarcode(String barcode){
        try {
            String encoded = URLEncoder.encode(barcode, StandardCharsets.UTF_8);
            URI uri = URI.create(openfoodHost + "/api/v2/product/" + encoded);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(20))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status < 200 || status >= 300) {
                throw new IllegalStateException("OpenFoodFacts responded with status " + status + ": " + response.body());
            }

            // The product sits under { "product": { ... } }
            record Wrapper(ProductIngredients product) {}
            Wrapper wrapped = objectMapper.readValue(response.body(), Wrapper.class);
            ProductIngredients pi = wrapped.product();
            if (pi != null) {
                // Ensure at least one of code/id is set to the barcode we searched
                if (pi.getCode() == null && pi.getId() == null) {
                    pi.setCode(barcode);
                }
            }
            return pi;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch product by barcode from OpenFoodFacts", e);
        }
    }

    public SearchProductResponse searchProduct(String searchTerms) {
        try {
            String encoded = URLEncoder.encode(searchTerms, StandardCharsets.UTF_8);
            String query = "/cgi/search.pl?search_terms=" + encoded + "&search_simple=1&action=process&json=1";
            URI uri = URI.create(openfoodHost + query);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofSeconds(20))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            if (status >= 200 && status < 300) {
                return objectMapper.readValue(response.body(), SearchProductResponse.class);
            }
            throw new IllegalStateException("OpenFoodFacts responded with status " + status + ": " + response.body());
        } catch (Exception e) {
            throw new RuntimeException("Failed to search products from OpenFoodFacts", e);
        }
    }

}
