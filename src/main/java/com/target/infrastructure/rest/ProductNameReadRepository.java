package com.target.infrastructure.rest;

import jakarta.inject.Singleton;
import org.json.JSONObject;

@Singleton
public class ProductNameReadRepository {
    private final ProductClient productClient;

    /**
     * Default constructor.
     *
     * @param productClient Rest Client to read Product Name.
     */
    public ProductNameReadRepository(ProductClient productClient) {
        this.productClient = productClient;
    }

    /**
     * This method
     *
     * @param restResponse Response from get product name rest call
     * @return Product Name.
     * @throws Exception
     */
    public String fetchProductName(Long id) throws Exception {
        return fetchProductName(productClient.getProductName(id));
    }

    /**
     * This method
     *
     * @param restResponse Response from get product name rest call
     * @return Product Name.
     * @throws Exception
     */
    private String fetchProductName(String restResponse) throws Exception {
        String productName = " ";
        if(restResponse != null) {
            JSONObject requestJsonObject = new JSONObject(restResponse);
            JSONObject data = requestJsonObject.getJSONObject("data");
            JSONObject product = data.getJSONObject("product");
            JSONObject item = product.getJSONObject("item");
            JSONObject productDescription = item.getJSONObject("product_description");
            productName = productDescription.getString("title");
        }
        return productName;
    }
}
