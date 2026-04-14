package io.ggroup.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "orderdetails")
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "unitprice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    // Constructors
    public OrderDetails() {
    }

    public OrderDetails(Order order, Ticket ticket, BigDecimal unitPrice, Integer quantity, Seller seller) {
        this.id = new OrderDetailsId(order.getOrderId(), ticket.getTicketId());
        this.order = order;
        this.ticket = ticket;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.seller = seller;
    }

    // Getters and Setters
    public OrderDetailsId getId() {
        return id;
    }

    public void setId(OrderDetailsId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Composite Primary Key Class
    @Embeddable
    public static class OrderDetailsId implements Serializable {
        
        @Column(name = "order_id")
        private Integer orderId;

        @Column(name = "ticket_id")
        private Integer ticketId;

        public OrderDetailsId() {
        }

        public OrderDetailsId(Integer orderId, Integer ticketId) {
            this.orderId = orderId;
            this.ticketId = ticketId;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public Integer getTicketId() {
            return ticketId;
        }

        public void setTicketId(Integer ticketId) {
            this.ticketId = ticketId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderDetailsId that = (OrderDetailsId) o;
            return Objects.equals(orderId, that.orderId) && Objects.equals(ticketId, that.ticketId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, ticketId);
        }
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
    
}
