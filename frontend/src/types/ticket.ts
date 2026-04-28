export interface TicketType {
  ticketType: string;
}

export interface Ticket {
  ticketId: number;
  ticketType: TicketType;
  event: { eventId: number };
  unitPrice: number;
  inStock: number;
  orderLimit: number | null;
}
