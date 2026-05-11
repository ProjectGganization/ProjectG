package io.ggroup.demo.dto;

import io.ggroup.demo.model.IssuedTicket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InspectResponseDTO {

    private Integer issuedTicketId;
    private String qrCode;
    private boolean used;

    private String ticketType;
    private BigDecimal unitPrice;

    private String eventTitle;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;

    private Integer orderId;
    private String customerFirstname;
    private String customerLastname;
    private String customerEmail;

    public InspectResponseDTO(IssuedTicket t) {
        this.issuedTicketId   = t.getIssuedTicketId();
        this.qrCode           = t.getQrCode();
        this.used             = t.isUsed();

        this.ticketType       = t.getTicket().getTicketType();
        this.unitPrice        = t.getTicket().getUnitPrice();

        this.eventTitle       = t.getTicket().getEvent().getTitle();
        this.eventStartTime   = t.getTicket().getEvent().getStartTime();
        this.eventEndTime     = t.getTicket().getEvent().getEndTime();

        this.orderId          = t.getOrder().getOrderId();
        this.customerFirstname = t.getOrder().getCustomer().getFirstname();
        this.customerLastname  = t.getOrder().getCustomer().getLastname();
        this.customerEmail     = t.getOrder().getCustomer().getEmail();
    }

    public Integer getIssuedTicketId()    { return issuedTicketId; }
    public String getQrCode()             { return qrCode; }
    public boolean isUsed()               { return used; }
    public String getTicketType()         { return ticketType; }
    public BigDecimal getUnitPrice()      { return unitPrice; }
    public String getEventTitle()         { return eventTitle; }
    public LocalDateTime getEventStartTime() { return eventStartTime; }
    public LocalDateTime getEventEndTime()   { return eventEndTime; }
    public Integer getOrderId()           { return orderId; }
    public String getCustomerFirstname()  { return customerFirstname; }
    public String getCustomerLastname()   { return customerLastname; }
    public String getCustomerEmail()      { return customerEmail; }
}
