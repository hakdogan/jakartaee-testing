package jugistanbul.ecommerce.validation;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.06.2020
 **/
@Path("validation")
public class ValidationController
{

    @GET
    @Path("{cardNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validate(@PathParam("cardNumber") final String cardNumber){
        final boolean isValid = validateCardNumber(cardNumber);

        if(!isValid){
            return Response.status(200)
                    .entity(Json.createObjectBuilder()
                            .add("message", "Invalid Credit Card Number")
                            .add("approval", false)
                            .build())
                    .build();
        }

        return Response.status(200)
                .entity(Json.createObjectBuilder()
                        .add("message", "Valid Credit Card Number")
                        .add("approval", true)
                        .build())
                .build();
    }

    @GET
    @Path("test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTEst(){
        return Response.status(200).entity(Json.createObjectBuilder().add("test", "success")
        .build()).build();
    }
    private boolean validateCardNumber(final String cardNumber){

        try {
            Long.parseLong(cardNumber);
            return true;
        } catch (NumberFormatException ex){

        }
        return false;
    }
}
