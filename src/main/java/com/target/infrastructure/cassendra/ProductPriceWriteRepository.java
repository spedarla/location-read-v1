package com.target.infrastructure.cassendra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.target.model.ProductPrice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * This is the DAO Object for productprices table which is used to
 * update the price information to Cassandra Database.
 *
 * @author Srilatha
 *
 */
@Singleton
public class ProductPriceWriteRepository {
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
    public void updatePrice(Long id, Float price) {
        CqlSession session = sessionFactory.session(config).build();
        PreparedStatement statement = session.prepare(
                "UPDATE myretail.productprices SET price = :price WHERE productID = :productID"
        );

        session.execute(statement.bind().setFloat("price", price).setLong("productID", id));
    }
}
