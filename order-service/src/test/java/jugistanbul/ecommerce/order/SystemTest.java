package jugistanbul.ecommerce.order;

import jugistanbul.ecommerce.order.controller.OrderController;
import jugistanbul.ecommerce.order.entity.OrderDemand;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jaxrs.RESTClient;
import org.microshed.testing.jupiter.MicroShedTest;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 23.06.2020
 **/
@MicroShedTest
@SharedContainerConfig(AppContainerConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SystemTest
{
    @RESTClient
    public static OrderController orderController;

    @Test
    @Order(1)
    public void invalidCardNumberTest(){
        final OrderDemand order = new OrderDemand(1, 1, "", "hello");
        final Response response = orderController.saveOrder(order);
        Assert.assertEquals(false, response.readEntity(JsonObject.class).getBoolean("approval"));
    }

    @Test
    @Order(2)
    public void validCardNumberTest(){
        final OrderDemand order = new OrderDemand(1, 1, "", "1234567");
        final Response response = orderController.saveOrder(order);
        Assert.assertNotNull(response.readEntity(OrderDemand.class).getId());
    }

    @Test
    @Order(3)
    public void getAllOrderTest(){
        final Response response = orderController.getAllOrders();
        final List<OrderDemand> list = response.readEntity(List.class);
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    @Order(4)
    public void getAllOrderByProductIdTest(){
        final Response response = orderController.getAllOrdersByProductId(1);
        final List<OrderDemand> list = response.readEntity(List.class);
        Assert.assertFalse(list.isEmpty());
    }
}
