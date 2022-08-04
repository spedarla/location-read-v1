package com.target;

import com.target.model.Product;
import com.target.model.ProductPrice;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
class LocationReadV1Test {
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void testProductDetails() throws JSONException {
        HttpRequest<String> request = HttpRequest.GET("/products/13860428");
        String body = client.toBlocking().retrieve(request);

        //Validate Status Code
        assertEquals(200, client.toBlocking().exchange(request).code());

        //Validate Response Body
        assertNotNull(body);
        JSONObject json = new JSONObject(body);
        assertEquals(13860428, json.get("id"));
        assertEquals("The Big Lebowski (Blu-ray)", json.getString("name"));
        assertEquals("USD", json.getJSONObject("current_price").getString("currency_code"));
        assertEquals(14.49, json.getJSONObject("current_price").getDouble("value"));
    }

    @Test
    public void testUpdateProductDetails() throws JSONException {
        double random = Math.floor(10 + Math.random() * (40 - 10));
        ProductPrice productPrice = new ProductPrice();
        productPrice.setValue((float) random);
        productPrice.setCurrency_code("USD");

        Product product = new Product();
        product.setId(13264003L);
        product.setName("Jif Natural Creamy Peanut Butter - 40oz");
        product.setCurrent_price(productPrice);

        HttpRequest request = HttpRequest.PUT("/products/13860428", product);

        //Validate Status Code
        assertEquals(202, client.toBlocking().exchange(request).code());

        HttpRequest<String> getRequest = HttpRequest.GET("/products/13264003");
        String body = client.toBlocking().retrieve(getRequest);
        //Validate Updated Price
        assertNotNull(body);
        JSONObject json = new JSONObject(body);
        assertEquals(random, json.getJSONObject("current_price").getDouble("value"));
    }
}
