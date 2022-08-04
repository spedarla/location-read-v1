package com.target.service;

import com.target.infrastructure.cassendra.ProductPriceReadRepository;
import com.target.infrastructure.cassendra.ProductPriceWriteRepository;
import com.target.infrastructure.rest.ProductClient;
import com.target.infrastructure.rest.ProductNameReadRepository;
import com.target.model.Product;
import com.target.model.ProductPrice;
import jakarta.inject.Inject;

/**
 * This class acts as a middle layer between back-end service providers and
 * front-end REST API controllers. It constructs productObject for GET API by
 * calling ProductNameReadRepository and ProductPriceRepository service. It also
 * updates price of the product for PUT API.
 *
 * @author Srilatha
 *
 */
public class ProductService {
    @Inject
    private ProductClient productClient;

    @Inject
    private final ProductNameReadRepository productNameReadRepository;

    @Inject
    private final ProductPriceReadRepository productPriceReadRepository;

    @Inject
    private final ProductPriceWriteRepository productPriceWriteRepository;

    /**
     * Default constructor.
     *
     * @param productPriceReadRepository  Repository to read Product Price.
     * @param productNameReadRepository   Repository to read Product Name.
     * @param productPriceWriteRepository
     */
    public ProductService(
            ProductPriceReadRepository productPriceReadRepository,
            ProductNameReadRepository productNameReadRepository,
            ProductPriceWriteRepository productPriceWriteRepository) {
        this.productPriceReadRepository = productPriceReadRepository;
        this.productNameReadRepository = productNameReadRepository;
        this.productPriceWriteRepository = productPriceWriteRepository;
    }

    /**
     * This method calls productClient and productPriceRepository to get
     * name and price of the product.
     *
     * @param id productID of type Long
     * @return Product object.
     * @throws Exception
     */
    public Product getProductData(Long id) throws Exception {
        //Fetch Price Info
        ProductPrice current_price = productPriceReadRepository.fetchPrice(id);

        //Fetch Product Name
        String name = productNameReadRepository.fetchProductName(id);

        return new Product(id, name, current_price );
    }

    public void updateProduct(Product product) {
        //Update Product Price
        productPriceWriteRepository.updatePrice(product.getId(), product.getCurrent_price().getValue());
    }
}
