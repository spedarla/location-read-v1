package com.target.controller;

import com.target.exception.ProductNotFoundException;
import com.target.model.Product;
import com.target.service.ProductService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import jakarta.inject.Inject;

/**
 * This class contains RESTFUL services to get product details(i.e., name and
 * price of the product) and also to update the price of the product.
 *
 * @author Srilatha
 * @version 1.0
 */
@Controller
public class RestController {
    @Inject
    private ProductService productService;

    /**
     * This is a HTTP GET method which is used to get product details like name
     * and price information.
     *
     * @param id of type long is given as input parameter to the method
     * @return an object containing productID,name and price information which
     *         is of type Product Class
     * @throws Exception
     */
    @Get("/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Product> findProduct(@QueryValue("id") Long id) throws Exception {
        // This method throws IllegalArgumentException if productID value is
        // negative or is of type null.
        if (id == null || id < 0) {
            throw new IllegalArgumentException("productID parameter cannot be null or less than 0");
        }

        Product product = productService.getProductData(id);

        if (product.getName() == " " || product.getId() == null)
            throw new ProductNotFoundException("ProductID you are searching is not found");

        return HttpResponse.ok(product);
    }

    /**
     * This is a HTTP PUT method which is used to update product price in data store
     *
     * @param product
     */
    @Put("/products/{id}")
    public HttpResponse updateProduct(@QueryValue("id") Long id, @Body Product product) {
        productService.updateProduct(product);
        return HttpResponse.accepted();
    }
}
