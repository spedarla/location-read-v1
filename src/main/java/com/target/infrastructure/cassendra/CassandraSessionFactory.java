package com.target.infrastructure.cassendra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.internal.core.config.typesafe.DefaultDriverConfigLoader;
import com.typesafe.config.ConfigFactory;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.naming.conventions.StringConvention;
import io.micronaut.core.value.PropertyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates cassandra cluster for each configuration bean.
 */
@Factory
public class CassandraSessionFactory implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraSessionFactory.class);
    private final List<CqlSession> sessions = new ArrayList<>(2);
    private final PropertyResolver resolver;

    /**
     * Default constructor.
     *
     * @param propertyResolver Property resolve for application configurations
     */
    public CassandraSessionFactory(PropertyResolver propertyResolver) {
        this.resolver = propertyResolver;
    }

    /**
     * Creates the {@link CqlSessionBuilder} bean for the given configuration.
     *
     * @param configuration The cassandra configuration bean
     * @return A {@link CqlSession} bean
     */
    @EachBean(CassandraConfiguration.class)
    public CqlSessionBuilder session(CassandraConfiguration configuration) {
        try {
            return CqlSession.builder().withConfigLoader(new DefaultDriverConfigLoader(() -> {
                ConfigFactory.invalidateCaches();
                String prefix = configuration.getName();
                return ConfigFactory.parseMap(this.resolver.getProperties(CassandraConfiguration.PREFIX + "." + prefix, StringConvention.RAW)).withFallback(ConfigFactory.load().getConfig(DefaultDriverConfigLoader.DEFAULT_ROOT_PATH));
            }));
        } catch (Exception e) {
            LOG.error(String.format("Failed to instantiate CQL session: %s", e.getMessage()), e);
            throw e;
        }
    }

    /**
     * Creates the {@link CqlSession} bean for the given configuration.
     *
     * @param builder The {@link CqlSessionBuilder}
     * @return A {@link CqlSession} bean
     */
    @EachBean(CqlSessionBuilder.class)
    @Bean(preDestroy = "close")
    public CqlSession cassandraCluster(CqlSessionBuilder builder) {
        CqlSession session = builder.build();
        this.sessions.add(session);
        return session;
    }

    /**
     * closes all active {@link CqlSession}.
     */
    @Override
    @PreDestroy
    public void close() {
        for (CqlSession session : sessions) {
            try {
                session.close();
            } catch (Exception e) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn(String.format("Error closing data source [%s]: %s", session, e.getMessage()), e);
                }
            }
        }
    }
}
