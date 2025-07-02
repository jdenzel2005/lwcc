package de.lexware.cc.customers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class DatabaseTestcontainersConfiguration {

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresContainer() {
        PostgreSQLContainer<?> pc = new PostgreSQLContainer<>(
            DockerImageName
                .parse("postgres:17")
                .asCompatibleSubstituteFor("postgres")
        );

        pc.withUsername("postgres").withPassword("postgres")
            .withInitScript("db/init/init.sql");
        return pc;
    }
}
