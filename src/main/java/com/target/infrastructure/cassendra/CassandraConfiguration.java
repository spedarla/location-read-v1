package com.target.infrastructure.cassendra;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

/**
 * Allows the configuration of a Cassandra Cluster connection using the datastax driver.
 *
 * The client is able to be configured to multiple clusters. If there are multiple configuration keys, default can be
 * used to denote the primary cluster bean.
 *
 */
@EachProperty(value = CassandraConfiguration.PREFIX, primary = "default")
public class CassandraConfiguration {
    public static final String PREFIX = "cassandra";
    private final String name;

    /**
     * @param name The provider name
     */
    public CassandraConfiguration(@Parameter String name) {
        this.name = name;
    }

    /**
     * @return The provider name
     */
    public String getName() {
        return name;
    }
}
