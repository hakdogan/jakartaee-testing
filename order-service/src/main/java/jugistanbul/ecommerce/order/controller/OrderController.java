package jugistanbul.ecommerce.order.controller;

import jugistanbul.ecommerce.order.client.ValidationServiceClient;
import jugistanbul.ecommerce.order.dao.OrderDAO;
import jugistanbul.ecommerce.order.entity.OrderDemand;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.06.2020
 **/
@ApplicationScoped
@Path("order")
public class OrderController
{
    @Inject
    private OrderDAO dao;

    @Inject
    private ValidationServiceClient client;

    @POST
    @Path("save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOrder(final OrderDemand order){

        final JsonObject object =
                client.callValidationServiceEndPoint(order.getCardNumber());

        final boolean approval = object.getBoolean("approval");
        if(!approval){
            return Response.status(200).entity(object).build();
        }
        dao.saveOrder(order);
        return Response.status(200).entity(order).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public Response getAllOrders(){
        return Response.status(200).entity(dao.getAllOrder()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all/{productId}")
    public Response getAllOrdersByProductId(@PathParam("productId") int productId){
        return Response.status(200)
                .entity(dao.getAllOrderByProductId(productId)).build();
    }
}
