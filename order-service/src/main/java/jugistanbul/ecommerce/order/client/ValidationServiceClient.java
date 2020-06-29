package jugistanbul.ecommerce.order.client;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.06.2020
 **/
@ApplicationScoped
public class ValidationServiceClient
{
    private WebTarget target;
    private Client client;

    private static final String DEFAULT_VALIDATION_SERVICE_HOST_URL = "http://localhost:9080";
    private static final String VALIDATION_SERVICE_PATH = "/api/validation/{cardNumber}";


    @PostConstruct
    public void initClient() {
        final ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        client = clientBuilder.build();
    }

    public JsonObject callValidationServiceEndPoint(final String cardNumber){
        target = client.target(resolveRemoteHostName())
                .path(VALIDATION_SERVICE_PATH)
                .resolveTemplate("cardNumber", cardNumber);

        final Response response = target
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        return response.readEntity(JsonObject.class);
    }

    private String resolveRemoteHostName(){
        final String hostName = System.getenv("VALIDATION_SERVICE_HOSTNAME");
        if(null != hostName ){
            return DEFAULT_VALIDATION_SERVICE_HOST_URL.replace("localhost", hostName);
        }

        return DEFAULT_VALIDATION_SERVICE_HOST_URL;
    }

    @PreDestroy
    public void tearDown() {
        client.close();
    }
}
