package de.lexware.cc.customers;

import de.lexware.cc.customers.infrastructure.persistence.JpaCustomerRepository;
import jakarta.persistence.EntityManager;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class ClearDatabaseAction {

    private ClearDatabaseAction() {
    }

    private static void run(ApplicationContext applicationContext) {
        Flyway flyway = applicationContext.getBean(Flyway.class);
        flyway.clean();
        flyway.migrate();
    }

    public static void run(ExtensionContext extensionContext) {
        ApplicationContext context = SpringExtension.getApplicationContext(extensionContext);
        run(context);
    }
}
