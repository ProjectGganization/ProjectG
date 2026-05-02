package io.ggroup.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class CreateOrderDetailsRequest {
    @NotNull
    private Integer orderId;

    @NotNull
    private Integer ticketId;

    @Min(1)
    private int quantity;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
   
}
