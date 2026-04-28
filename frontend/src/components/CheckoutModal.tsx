import { useState } from 'react';
import { Ticket } from '../types/ticket';
import { createCustomer, createOrder, createOrderDetail } from '../api/orderService';

interface SelectedTicket {
  ticket: Ticket;
  quantity: number;
}

interface CheckoutModalProps {
  selected: SelectedTicket[];
  subtotal: number;
  serviceFee: number;
  onClose: () => void;
  onSuccess: () => void;
}

type Step = 'form' | 'submitting' | 'success' | 'error';

const PAYMENT_METHODS = [
  { value: 'card', label: 'Card' },
  { value: 'bank', label: 'Bank Transfer' },
  { value: 'cash', label: 'Cash' },
];

const CheckoutModal = ({ selected, subtotal, serviceFee, onClose, onSuccess }: CheckoutModalProps) => {
  const [step, setStep] = useState<Step>('form');
  const [orderId, setOrderId] = useState<number | null>(null);
  const [errorMessage, setErrorMessage] = useState('');

  const [form, setForm] = useState({
    firstname: '',
    lastname: '',
    email: '',
    phone: '',
    paymentMethod: 'card',
  });

  const set = (field: keyof typeof form) => (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) =>
    setForm((prev) => ({ ...prev, [field]: e.target.value }));

  const total = subtotal + serviceFee;

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setStep('submitting');
    try {
      const customer = await createCustomer({
        firstname: form.firstname,
        lastname: form.lastname,
        email: form.email,
        phone: form.phone,
      });

      const order = await createOrder(customer.customerId, form.paymentMethod);

      await Promise.all(
        selected.map((s) =>
          createOrderDetail(order.orderId, s.ticket.ticketId, s.ticket.unitPrice, s.quantity)
        )
      );

      setOrderId(order.orderId);
      setStep('success');
      onSuccess();
    } catch {
      setErrorMessage('Something went wrong. Please try again.');
      setStep('error');
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
      {/* Backdrop */}
      <div className="absolute inset-0 bg-black/40 backdrop-blur-sm" onClick={step === 'form' ? onClose : undefined} />

      {/* Modal */}
      <div className="relative w-full max-w-lg bg-surface rounded-xl shadow-2xl overflow-hidden">
        {/* Header */}
        <div className="flex items-center justify-between px-8 py-6 border-b border-outline-variant/20">
          <h2 className="text-lg font-bold">
            {step === 'success' ? 'Order Confirmed' : 'Complete Your Order'}
          </h2>
          <button onClick={onClose} className="text-on-surface-variant hover:text-on-surface transition-colors">
            <span className="material-symbols-outlined">close</span>
          </button>
        </div>

        {/* Success */}
        {step === 'success' && (
          <div className="px-8 py-12 text-center">
            <span className="material-symbols-outlined text-6xl text-primary mb-4 block"
              style={{ fontVariationSettings: "'FILL' 1" }}>
              check_circle
            </span>
            <h3 className="text-2xl font-black tracking-tight mb-2">You're going!</h3>
            <p className="text-on-surface-variant mb-1">Order #{orderId} has been placed.</p>
            <p className="text-on-surface-variant text-sm">A confirmation will be sent to {form.email}.</p>
            <button
              onClick={onClose}
              className="mt-8 signature-gradient text-on-primary px-8 py-3 rounded-md font-bold text-sm tracking-widest uppercase hover:opacity-90 active:scale-[0.98] transition-all"
            >
              Done
            </button>
          </div>
        )}

        {/* Error */}
        {step === 'error' && (
          <div className="px-8 py-12 text-center">
            <span className="material-symbols-outlined text-6xl text-error mb-4 block">error</span>
            <p className="text-on-surface-variant mb-6">{errorMessage}</p>
            <button
              onClick={() => setStep('form')}
              className="text-primary font-bold hover:underline underline-offset-4"
            >
              Try again
            </button>
          </div>
        )}

        {/* Form */}
        {(step === 'form' || step === 'submitting') && (
          <form onSubmit={handleSubmit}>
            <div className="px-8 py-6 space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-xs font-semibold text-on-surface-variant mb-1">First Name</label>
                  <input
                    required
                    value={form.firstname}
                    onChange={set('firstname')}
                    className="w-full px-3 py-2 rounded-lg bg-surface-container border border-outline-variant/40 text-sm focus:outline-none focus:border-primary transition-colors"
                    placeholder="Matti"
                  />
                </div>
                <div>
                  <label className="block text-xs font-semibold text-on-surface-variant mb-1">Last Name</label>
                  <input
                    required
                    value={form.lastname}
                    onChange={set('lastname')}
                    className="w-full px-3 py-2 rounded-lg bg-surface-container border border-outline-variant/40 text-sm focus:outline-none focus:border-primary transition-colors"
                    placeholder="Mallikas"
                  />
                </div>
              </div>

              <div>
                <label className="block text-xs font-semibold text-on-surface-variant mb-1">Email</label>
                <input
                  required
                  type="email"
                  value={form.email}
                  onChange={set('email')}
                  className="w-full px-3 py-2 rounded-lg bg-surface-container border border-outline-variant/40 text-sm focus:outline-none focus:border-primary transition-colors"
                  placeholder="matti@example.com"
                />
              </div>

              <div>
                <label className="block text-xs font-semibold text-on-surface-variant mb-1">Phone</label>
                <input
                  required
                  type="tel"
                  value={form.phone}
                  onChange={set('phone')}
                  className="w-full px-3 py-2 rounded-lg bg-surface-container border border-outline-variant/40 text-sm focus:outline-none focus:border-primary transition-colors"
                  placeholder="+358401234567"
                />
              </div>

              <div>
                <label className="block text-xs font-semibold text-on-surface-variant mb-1">Payment Method</label>
                <select
                  value={form.paymentMethod}
                  onChange={set('paymentMethod')}
                  className="w-full px-3 py-2 rounded-lg bg-surface-container border border-outline-variant/40 text-sm focus:outline-none focus:border-primary transition-colors"
                >
                  {PAYMENT_METHODS.map((m) => (
                    <option key={m.value} value={m.value}>{m.label}</option>
                  ))}
                </select>
              </div>

              {/* Order summary */}
              <div className="pt-4 border-t border-outline-variant/20 space-y-2">
                {selected.map((s) => (
                  <div key={s.ticket.ticketId} className="flex justify-between text-sm">
                    <span className="text-on-surface-variant">{s.quantity}x {s.ticket.ticketType.ticketType}</span>
                    <span className="font-semibold">€{(s.ticket.unitPrice * s.quantity).toFixed(2)}</span>
                  </div>
                ))}
                <div className="flex justify-between text-sm">
                  <span className="text-on-surface-variant">Service Fee</span>
                  <span className="font-semibold">€{serviceFee.toFixed(2)}</span>
                </div>
                <div className="flex justify-between items-center pt-2 border-t border-outline-variant/20">
                  <span className="font-bold">Total</span>
                  <span className="text-2xl font-black text-primary tracking-tighter">€{total.toFixed(2)}</span>
                </div>
              </div>
            </div>

            <div className="px-8 pb-8">
              <button
                type="submit"
                disabled={step === 'submitting'}
                className="w-full signature-gradient text-on-primary py-4 rounded-md font-bold text-sm tracking-widest uppercase transition-all hover:opacity-90 active:scale-[0.98] disabled:opacity-60 disabled:cursor-not-allowed flex items-center justify-center gap-2"
              >
                {step === 'submitting' ? (
                  <>
                    <span className="material-symbols-outlined animate-spin text-sm">progress_activity</span>
                    Processing...
                  </>
                ) : (
                  'Place Order'
                )}
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
};

export default CheckoutModal;
