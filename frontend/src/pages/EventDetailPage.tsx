import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Event, formatEventDate, formatEventLocation } from '../types/event';
import { Ticket } from '../types/ticket';
import { getEvent } from '../api/eventService';
import { getTicketsByEvent } from '../api/ticketService';
import CheckoutModal from '../components/CheckoutModal';
import { resolveEventImage } from '../utils/categoryImage';

const SERVICE_FEE = 12.50;

const EventDetailPage = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [event, setEvent] = useState<Event | null>(null);
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [quantities, setQuantities] = useState<Record<number, number>>({});
  const [showCheckout, setShowCheckout] = useState(false);

  useEffect(() => {
    const eventId = Number(id);
    Promise.all([getEvent(eventId), getTicketsByEvent(eventId)])
      .then(([eventData, ticketData]) => {
        setEvent(eventData);
        setTickets(ticketData);
        const initial: Record<number, number> = {};
        ticketData.forEach((t) => { initial[t.ticketId] = 0; });
        setQuantities(initial);
      })
      .catch(() => setError('Could not load event details.'))
      .finally(() => setLoading(false));
  }, [id]);

  const updateQuantity = (ticketId: number, delta: number) => {
    setQuantities((prev) => {
      const ticket = tickets.find((t) => t.ticketId === ticketId);
      const max = ticket?.orderLimit ?? ticket?.inStock ?? 0;
      const next = Math.min(max, Math.max(0, (prev[ticketId] ?? 0) + delta));
      return { ...prev, [ticketId]: next };
    });
  };

  const subtotal = tickets.reduce((sum, t) => sum + t.unitPrice * (quantities[t.ticketId] ?? 0), 0);
  const hasTickets = subtotal > 0;
  const total = hasTickets ? subtotal + SERVICE_FEE : 0;

  const selectedTickets = tickets
    .filter((t) => (quantities[t.ticketId] ?? 0) > 0)
    .map((t) => ({ ticket: t, quantity: quantities[t.ticketId] }));

  if (loading) {
    return (
      <main className="pt-24 pb-20 px-6 flex justify-center">
        <span className="material-symbols-outlined animate-spin text-primary text-4xl mt-24">progress_activity</span>
      </main>
    );
  }

  if (error || !event) {
    return (
      <main className="pt-24 pb-20 px-6 text-center">
        <span className="material-symbols-outlined text-4xl mb-4 block text-on-surface-variant">error</span>
        <p className="text-on-surface-variant">{error ?? 'Event not found.'}</p>
        <button onClick={() => navigate('/')} className="mt-6 text-primary font-semibold hover:underline">
          Back to Events
        </button>
      </main>
    );
  }

  return (
    <main className="pt-24 pb-20 px-6">
      <div className="max-w-7xl mx-auto">
        {/* Back button */}
        <button
          onClick={() => { navigate(-1); window.scrollTo(0, 0); }}
          className="mb-8 inline-flex items-center gap-1 text-sm font-semibold text-primary hover:underline underline-offset-4"
        >
          <span className="material-symbols-outlined text-sm">arrow_back</span>
          Back to Events
        </button>

        {/* Hero Section */}
        <section className="grid grid-cols-1 lg:grid-cols-12 gap-8 mb-16">
          <div className="lg:col-span-7 flex flex-col justify-center">
            <div className="mb-4">
              <span className="inline-flex items-center px-3 py-1 rounded-full bg-secondary-container text-on-secondary-container text-[0.6875rem] font-medium tracking-widest uppercase">
                {event.category.category}
              </span>
            </div>
            <h1 className="text-[3.5rem] font-black tracking-tighter leading-[1.1] mb-6 text-on-surface">
              {event.title}
            </h1>
            {event.description && (
              <p className="text-on-surface-variant mb-6 leading-relaxed">{event.description}</p>
            )}
            <div className="space-y-4">
              <div className="flex items-center gap-3">
                <span className="material-symbols-outlined text-primary">calendar_today</span>
                <span className="text-on-surface-variant font-medium">{formatEventDate(event.startTime)}</span>
              </div>
              <div className="flex items-center gap-3">
                <span className="material-symbols-outlined text-primary">schedule</span>
                <span className="text-on-surface-variant font-medium">
                  Doors open at {new Date(event.startTime).toLocaleTimeString('en-GB', { hour: '2-digit', minute: '2-digit' })}
                </span>
              </div>
              <div className="flex items-center gap-3">
                <span className="material-symbols-outlined text-primary">location_on</span>
                <span className="text-on-surface-variant font-medium">{formatEventLocation(event.venue)}</span>
              </div>
            </div>
          </div>

          <div className="lg:col-span-5 relative h-[400px] rounded-xl overflow-hidden shadow-2xl">
            <img
              alt={event.title}
              className="w-full h-full object-cover"
              src={resolveEventImage(event.photo, event.category.category)}
            />
            <div className="absolute inset-0 bg-gradient-to-t from-black/40 to-transparent" />
          </div>
        </section>

        {/* Content Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12">
          {/* Ticket Selection */}
          <div className="lg:col-span-8">
            <h2 className="text-2xl font-bold tracking-tight mb-8">Select Your Passage</h2>

            {tickets.length === 0 ? (
              <p className="text-on-surface-variant">No tickets available for this event.</p>
            ) : (
              <div className="space-y-6">
                {tickets.map((ticket) => (
                  <div
                    key={ticket.ticketId}
                    className="group p-8 rounded-xl bg-surface-container-lowest transition-all duration-300 hover:bg-surface-container-low"
                  >
                    <div className="flex flex-col md:flex-row md:items-center justify-between gap-6">
                      <div className="flex-1">
                        <h3 className="text-xl font-bold mb-2">{ticket.ticketType.ticketType}</h3>
                        <div className="flex flex-wrap gap-2">
                          <span className="text-[0.6875rem] px-2 py-0.5 rounded bg-surface-container-high text-on-surface-variant">
                            {ticket.inStock} left
                          </span>
                          {ticket.orderLimit && (
                            <span className="text-[0.6875rem] px-2 py-0.5 rounded bg-surface-container-high text-on-surface-variant">
                              Max {ticket.orderLimit} per order
                            </span>
                          )}
                        </div>
                      </div>

                      <div className="flex items-center justify-between md:flex-col md:items-end gap-4 min-w-[140px]">
                        <div className="text-2xl font-bold tracking-tighter">
                          <span className="text-outline text-lg font-normal">€</span>
                          {ticket.unitPrice.toFixed(2)}
                        </div>
                        <div className="flex items-center border border-outline-variant/20 rounded-lg bg-surface p-1">
                          <button
                            onClick={() => updateQuantity(ticket.ticketId, -1)}
                            className="w-8 h-8 flex items-center justify-center hover:bg-surface-container rounded-md transition-colors"
                            aria-label="Decrease quantity"
                          >
                            <span className="material-symbols-outlined text-sm">remove</span>
                          </button>
                          <span className="w-10 text-center font-bold text-sm">
                            {quantities[ticket.ticketId] ?? 0}
                          </span>
                          <button
                            onClick={() => updateQuantity(ticket.ticketId, 1)}
                            disabled={ticket.inStock === 0}
                            className="w-8 h-8 flex items-center justify-center hover:bg-surface-container rounded-md transition-colors text-primary disabled:opacity-30 disabled:cursor-not-allowed"
                            aria-label="Increase quantity"
                          >
                            <span className="material-symbols-outlined text-sm">add</span>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* Sidebar */}
          <div className="lg:col-span-4">
            <div className="sticky top-28 space-y-8">
              {/* Order Summary */}
              <div className="p-8 rounded-xl bg-surface-container shadow-sm">
                <h3 className="text-lg font-bold mb-6">Order Summary</h3>
                <div className="space-y-4 mb-8">
                  {hasTickets ? (
                    <>
                      {tickets.filter((t) => (quantities[t.ticketId] ?? 0) > 0).map((t) => (
                        <div key={t.ticketId} className="flex justify-between text-sm">
                          <span className="text-on-surface-variant">
                            {quantities[t.ticketId]}x {t.ticketType.ticketType}
                          </span>
                          <span className="font-bold">
                            €{(t.unitPrice * quantities[t.ticketId]).toFixed(2)}
                          </span>
                        </div>
                      ))}
                      <div className="flex justify-between text-sm">
                        <span className="text-on-surface-variant">Service Fee</span>
                        <span className="font-bold">€{SERVICE_FEE.toFixed(2)}</span>
                      </div>
                    </>
                  ) : (
                    <p className="text-sm text-on-surface-variant">No tickets selected yet.</p>
                  )}

                  <div className="pt-4 border-t border-outline-variant/20 flex justify-between items-end">
                    <span className="text-sm font-medium">Total</span>
                    <div className="text-3xl font-black text-primary tracking-tighter">
                      €{total.toFixed(2)}
                    </div>
                  </div>
                </div>

                <button
                  disabled={!hasTickets}
                  onClick={() => setShowCheckout(true)}
                  className="w-full signature-gradient text-on-primary py-4 rounded-md font-bold text-sm tracking-widest uppercase transition-all hover:opacity-90 active:scale-[0.98] disabled:opacity-40 disabled:cursor-not-allowed"
                >
                  Complete Purchase
                </button>
                <p className="text-[0.6875rem] text-center text-on-surface-variant mt-4">
                  Secure checkout by ProjectG. <br />All sales are final.
                </p>
              </div>

              {/* Venue Info */}
              <div className="rounded-xl overflow-hidden bg-surface-container-lowest border border-outline-variant/10">
                <div className="p-6">
                  <h4 className="font-bold mb-1">{event.venue.name}</h4>
                  <p className="text-xs text-on-surface-variant leading-relaxed">
                    {event.venue.address}<br />
                    {event.venue.postalCode.postalCode} {event.venue.postalCode.city}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {showCheckout && (
        <CheckoutModal
          selected={selectedTickets}
          subtotal={subtotal}
          serviceFee={SERVICE_FEE}
          onClose={() => setShowCheckout(false)}
          onSuccess={() => {
            getTicketsByEvent(Number(id)).then(setTickets);
          }}
        />
      )}
    </main>
  );
};

export default EventDetailPage;
