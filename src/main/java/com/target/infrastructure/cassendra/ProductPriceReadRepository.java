package com.target.infrastructure.cassendra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.target.model.ProductPrice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * This is the DAO Object for productprices table which is used to get
 * the price information from Cassandra Database.
 *
 * @author Srilatha
 *
 */
@Singleton
public class ProductPriceReadRepository {
    @Inject
    CassandraConfiguration config;
    @Inject
    CassandraSessionFactory sessionFactory;

    /**
     * This method is responsible for retrieving the price information for a
     * given productID
     *
     * @param id productID of the product
     * @return returns ProductPrice object if productID is present in database
     *         otherwise null
     */
    public ProductPrice fetchPrice(Long id) {
        ProductPrice current_price = new ProductPrice();
        CqlSession session = sessionFactory.session(config).build();
        PreparedStatement statement = session.prepare(
                "SELECT * FROM myretail.productprices WHERE productID = :productID LIMIT 1"
        );

        Row row = session.execute(statement.bind().setLong("productID", id)).one();
        if(row != null) {
            current_price.setValue(row.getFloat("price"));
            current_price.setCurrency_code(row.getString("currency"));
        }

        return current_price;
    }
}
