package jugistanbul.ecommerce.order.dao;

import jugistanbul.ecommerce.order.entity.OrderDemand;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.06.2020
 **/
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
public class OrderDAO
{
    @PersistenceContext
    private EntityManager em;

    public void saveOrder(final OrderDemand order){
        em.persist(order);
    }

    public List<OrderDemand> getAllOrder(){
        return em.createNamedQuery("OrderDemand.findAll", OrderDemand.class).getResultList();
    }

    public List<OrderDemand> getAllOrderByProductId(final int productId){
        return em.createNamedQuery("OrderDemand.findByProductId", OrderDemand.class)
                .setParameter("id", productId)
                .getResultList();
    }
}
