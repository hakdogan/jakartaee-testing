package jugistanbul.ecommerce.order.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.06.2020
 **/

@Entity
@Table(name = "orderdemand")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQueries({@NamedQuery(name = "OrderDemand.findAll", query = "SELECT o FROM OrderDemand o"),
        @NamedQuery(name = "OrderDemand.findByProductId", query = "SELECT o FROM OrderDemand o WHERE "
                + "o.productId = :id")}
)
public class OrderDemand implements Serializable
{

    private static final long serialVersionUID = 2162030580384071637L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "productId")
    private int productId;

    @Column(name = "count")
    private int count;

    @Column(name = "orderNote")
    private String orderNote;

    @Column(name = "cardNumber")
    private String cardNumber;

    public OrderDemand(){}

    public OrderDemand(final int productId, final int count,
                       final String orderNote, final String cardNumber){
        this.productId = productId;
        this.count = count;
        this.orderNote = orderNote;
        this.cardNumber = cardNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDemand orders = (OrderDemand) o;
        return id == orders.id &&
                productId == orders.productId &&
                count == orders.count &&
                Objects.equals(orderNote, orders.orderNote) &&
                cardNumber.equals(orders.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, count, orderNote, cardNumber);
    }

    @Override
    public String toString() {
        return "OrderDemand{" +
                "id=" + id +
                ", productId=" + productId +
                ", count=" + count +
                ", orderNote='" + orderNote + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
