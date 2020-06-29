package jugistanbul.ecommerce.validation;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URL;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 22.06.2020
 **/
@RunWith(Arquillian.class)
public class ValidationServiceIT
{

    private final Client client;
    private static final String PATH = "api/validation/{cardNumber}";

    public ValidationServiceIT() {
        this.client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);
    }

    @ArquillianResource
    private URL baseURL;

    @Deployment
    public static WebArchive createDeployment() {
        final WebArchive archive = ShrinkWrap.create(WebArchive.class,
                "arquillian-validation-service.war")
                .addClasses(ValidationController.class, ValidationService.class);
        return archive;
    }

    @Test
    @RunAsClient
    @InSequence(1)
    public void invalidCardNumberTest() {

        final WebTarget webTarget = client.target(baseURL.toString()).path(PATH)
                .resolveTemplate("cardNumber", "hello");

        final Response response = webTarget.request().get();
        final JsonObject result = response.readEntity(JsonObject.class);
        Assert.assertFalse(result.getBoolean("approval"));
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void validCardNumberTest() {

        final WebTarget webTarget = client.target(baseURL.toString()).path(PATH)
                .resolveTemplate("cardNumber", "12345");

        final Response response = webTarget.request().get();
        final JsonObject result = response.readEntity(JsonObject.class);
        Assert.assertTrue(result.getBoolean("approval"));
    }

}
