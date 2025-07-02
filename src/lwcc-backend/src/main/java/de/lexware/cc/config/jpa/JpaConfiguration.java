package de.lexware.cc.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.lexware.cc.customers.infrastructure.persistence")
@EntityScan(basePackages = "de.lexware.cc.customers.infrastructure.persistence")
@EnableJpaAuditing
public class JpaConfiguration {
}
