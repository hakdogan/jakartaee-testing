package jugistanbul.ecommerce.order;

import org.microshed.testing.SharedContainerConfiguration;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.06.2020
 **/

public class AppContainerConfig implements SharedContainerConfiguration
{

    private static final String IMAGE_NAME = "hakdogan/validation-service:01";
    private static final String SERVICE_NAME = "validation-service";
    private static final String POSTGRES_NETWORK_ALIASES = "postgres";
    private static final String POSTGRES_USER = "testUser";
    private static final String POSTGRES_PASSWORD = "testPassword";
    private static final String POSTGRES_DB = "orderDB";

    private static final int VALIDATION_SERVICE_HTTP_PORT = 9080;
    private static final int VALIDATION_SERVICE_HTTPS_PORT = 9443;
    private static final int APPLICATION_SERVICE_HTTP_PORT = 9082;
    private static final int APPLICATION_SERVICE_HTTPS_PORT = 9445;
    private static final int POSTGRES_DEFAULT_PORT = 5432;

    private static Network network = Network.newNetwork();

    @Container
    public static GenericContainer validationService = new GenericContainer(IMAGE_NAME)
                    .withNetwork(network)
                    .withNetworkAliases(SERVICE_NAME)
                    .withEnv("HTTP_PORT", String.valueOf(VALIDATION_SERVICE_HTTP_PORT))
                    .withEnv("HTTPS_PORT", String.valueOf(VALIDATION_SERVICE_HTTPS_PORT))
                    .withExposedPorts(VALIDATION_SERVICE_HTTP_PORT)
                    .waitingFor(Wait.forListeningPort());

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>()
            .withNetwork(network)
            .withNetworkAliases(POSTGRES_NETWORK_ALIASES)
            .withUsername(POSTGRES_USER)
            .withPassword(POSTGRES_PASSWORD)
            .withDatabaseName(POSTGRES_DB)
            .withExposedPorts(POSTGRES_DEFAULT_PORT);

    @Container
    public static ApplicationContainer app = new ApplicationContainer()
            .withNetwork(network)
            .withEnv("POSTGRES_HOSTNAME", POSTGRES_NETWORK_ALIASES)
            .withEnv("POSTGRES_PORT", String.valueOf(POSTGRES_DEFAULT_PORT))
            .withEnv("POSTGRES_USER", POSTGRES_USER)
            .withEnv("POSTGRES_PASSWORD", POSTGRES_PASSWORD)
            .withEnv("POSTGRES_DB", POSTGRES_DB)
            .withEnv("VALIDATION_SERVICE_HOSTNAME", SERVICE_NAME)
            .withEnv("HTTP_PORT", String.valueOf(APPLICATION_SERVICE_HTTP_PORT))
            .withEnv("HTTPS_PORT", String.valueOf(APPLICATION_SERVICE_HTTPS_PORT))
            .withExposedPorts(APPLICATION_SERVICE_HTTP_PORT)
            .withAppContextRoot("/")
            .waitingFor(Wait.forListeningPort())
            .dependsOn(validationService, postgres);
}
