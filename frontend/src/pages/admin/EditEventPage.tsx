import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { TicketTier } from '../../types/ticketTier';
import TicketTierRow from '../../components/admin/TicketTierRow';
import { Venue } from '../../types/event';
import { getVenues } from '../../api/venueService';
import { Seller, getSellers } from '../../api/sellerService';
import { getEvent, updateEvent } from '../../api/eventService';

const CATEGORIES = ['music', 'sports', 'conference', 'theatre'];
const EVENT_STATUSES = ['upcoming', 'cancelled', 'finished'];

const EditEventPage = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    title: '',
    description: '',
    startTime: '',
    endTime: '',
    category: '',
    eventStatus: 'upcoming',
    venueId: '',
    sellerId: '',
  });
  const [tiers, setTiers] = useState<TicketTier[]>([]);
  const [venues, setVenues] = useState<Venue[]>([]);
  const [sellers, setSellers] = useState<Seller[]>([]);
  const [submitting, setSubmitting] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    Promise.all([
      getEvent(Number(id)),
      getVenues(),
      getSellers(),
    ]).then(([event, venueList, sellerList]) => {
      setVenues(venueList);
      setSellers(sellerList);
      setForm({
        title: event.title,
        description: event.description ?? '',
        startTime: event.startTime.slice(0, 16),
        endTime: event.endTime.slice(0, 16),
        category: event.category.category,
        eventStatus: event.eventStatus.eventStatus,
        venueId: String(event.venue.venueId),
        sellerId: String(event.seller?.sellerId ?? ''),
      });
    }).catch(() => setError('Could not load event.'))
      .finally(() => setLoading(false));
  }, [id]);

  const set = (field: keyof typeof form) =>
    (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) =>
      setForm((prev) => ({ ...prev, [field]: e.target.value }));

  const handleTierChange = (tierId: number, field: keyof TicketTier, value: string | number) => {
    setTiers((prev) => prev.map((t) => (t.id === tierId ? { ...t, [field]: value } : t)));
  };

  const handleTierDelete = (tierId: number) => {
    setTiers((prev) => prev.filter((t) => t.id !== tierId));
  };

  const handleAddTier = () => {
    const newId = tiers.length > 0 ? Math.max(...tiers.map((t) => t.id)) + 1 : 1;
    setTiers((prev) => [
      ...prev,
      { id: newId, type: 'New Ticket Type', unitPrice: 0, inStock: 0, orderLimit: 1 },
    ]);
  };

  const handleSave = async () => {
    if (!form.title || !form.startTime || !form.endTime || !form.venueId || !form.category || !form.sellerId) {
      setError('Please fill in all required fields.');
      return;
    }
    setSubmitting(true);
    setError(null);
    try {
      await updateEvent(Number(id), {
        title: form.title,
        description: form.description,
        startTime: form.startTime,
        endTime: form.endTime,
        eventStatus: { eventStatus: form.eventStatus },
        venue: { venueId: Number(form.venueId) },
        category: { category: form.category },
        seller: { sellerId: Number(form.sellerId) },
      });
      navigate('/admin');
    } catch {
      setError('Failed to save changes. Please try again.');
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center py-48">
        <span className="material-symbols-outlined animate-spin text-primary text-4xl">progress_activity</span>
      </div>
    );
  }

  return (
    <>
      <header className="sticky top-0 z-40 flex items-center justify-between px-8 w-full h-16 bg-white/70 backdrop-blur-md border-b border-slate-200/20">
        <span className="text-lg font-semibold text-slate-900">Edit Event</span>
        <div className="flex items-center gap-4">
          <button
            onClick={() => navigate('/admin')}
            className="text-on-surface-variant hover:text-primary text-sm font-medium transition-colors flex items-center gap-1"
          >
            <span className="material-symbols-outlined text-sm">arrow_back</span>
            Dashboard
          </button>
          <button
            onClick={handleSave}
            disabled={submitting}
            className="px-6 py-2 text-sm font-semibold text-on-primary bg-gradient-to-br from-primary to-primary-container rounded-md shadow-sm hover:opacity-90 active:scale-95 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {submitting ? 'Saving...' : 'Save Changes'}
          </button>
        </div>
      </header>

      <div className="max-w-5xl mx-auto p-8 space-y-12">
        {error && (
          <div className="p-4 bg-error-container text-on-error-container rounded-xl text-sm font-medium">
            {error}
          </div>
        )}

        <section className="space-y-6">
          <div className="flex items-baseline gap-4">
            <h2 className="text-2xl font-bold tracking-tight text-on-surface">Event Information</h2>
            <span className="h-px flex-1 bg-surface-container-high" />
          </div>
          <div className="grid grid-cols-1 gap-8">
            <div className="space-y-2">
              <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                Event Title <span className="text-error">*</span>
              </label>
              <input
                className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-xl px-6 py-4 text-xl font-medium focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                type="text"
                value={form.title}
                onChange={set('title')}
              />
            </div>
            <div className="space-y-2">
              <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                Description
              </label>
              <textarea
                className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-xl p-6 focus:ring-2 focus:ring-primary/10 focus:border-primary resize-none text-base outline-none"
                rows={5}
                value={form.description}
                onChange={set('description')}
              />
            </div>
          </div>
        </section>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-12">
          <section className="space-y-6">
            <div className="flex items-baseline gap-4">
              <h2 className="text-xl font-bold tracking-tight text-on-surface">Schedule</h2>
              <span className="h-px flex-1 bg-surface-container-high" />
            </div>
            <div className="grid grid-cols-2 gap-4">
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  Start <span className="text-error">*</span>
                </label>
                <input
                  className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                  type="datetime-local"
                  value={form.startTime}
                  onChange={set('startTime')}
                />
              </div>
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  End <span className="text-error">*</span>
                </label>
                <input
                  className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                  type="datetime-local"
                  value={form.endTime}
                  onChange={set('endTime')}
                />
              </div>
            </div>
          </section>

          <section className="space-y-6">
            <div className="flex items-baseline gap-4">
              <h2 className="text-xl font-bold tracking-tight text-on-surface">Classification</h2>
              <span className="h-px flex-1 bg-surface-container-high" />
            </div>
            <div className="grid grid-cols-2 gap-4">
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  Category <span className="text-error">*</span>
                </label>
                <select
                  className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                  value={form.category}
                  onChange={set('category')}
                >
                  {CATEGORIES.map((c) => (
                    <option key={c} value={c}>{c}</option>
                  ))}
                </select>
              </div>
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  Status
                </label>
                <select
                  className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                  value={form.eventStatus}
                  onChange={set('eventStatus')}
                >
                  {EVENT_STATUSES.map((s) => (
                    <option key={s} value={s}>{s.charAt(0).toUpperCase() + s.slice(1)}</option>
                  ))}
                </select>
              </div>
            </div>
          </section>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-12">
          <section className="space-y-6">
            <div className="flex items-baseline gap-4">
              <h2 className="text-xl font-bold tracking-tight text-on-surface">Location</h2>
              <span className="h-px flex-1 bg-surface-container-high" />
            </div>
            <div className="space-y-2">
              <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                Venue <span className="text-error">*</span>
              </label>
              <select
                className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                value={form.venueId}
                onChange={set('venueId')}
              >
                {venues.map((v) => (
                  <option key={v.venueId} value={v.venueId}>
                    {v.name}, {v.postalCode.city}
                  </option>
                ))}
              </select>
            </div>
          </section>

          <section className="space-y-6">
            <div className="flex items-baseline gap-4">
              <h2 className="text-xl font-bold tracking-tight text-on-surface">Seller</h2>
              <span className="h-px flex-1 bg-surface-container-high" />
            </div>
            <div className="space-y-2">
              <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                Assigned Seller <span className="text-error">*</span>
              </label>
              <select
                className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                value={form.sellerId}
                onChange={set('sellerId')}
              >
                {sellers.map((s) => (
                  <option key={s.sellerId} value={s.sellerId}>
                    {s.name} ({s.email})
                  </option>
                ))}
              </select>
            </div>
          </section>
        </div>

        {tiers.length > 0 && (
          <section className="space-y-6">
            <div className="flex items-baseline gap-4">
              <h2 className="text-xl font-bold tracking-tight text-on-surface">Ticket Tiers</h2>
              <span className="h-px flex-1 bg-surface-container-high" />
            </div>
            <div className="bg-surface-container-lowest border border-outline-variant/10 rounded-xl overflow-hidden">
              <table className="w-full text-left">
                <thead>
                  <tr className="bg-surface-container-low/50">
                    <th className="px-6 py-4 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant">Ticket Type</th>
                    <th className="px-6 py-4 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant text-right">Unit Price (€)</th>
                    <th className="px-6 py-4 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant text-right">In Stock</th>
                    <th className="px-6 py-4 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant text-right">Order Limit</th>
                    <th className="px-6 py-4 w-16" />
                  </tr>
                </thead>
                <tbody className="divide-y divide-surface-container-low">
                  {tiers.map((tier) => (
                    <TicketTierRow
                      key={tier.id}
                      tier={tier}
                      onChange={handleTierChange}
                      onDelete={handleTierDelete}
                    />
                  ))}
                </tbody>
              </table>
              <button
                className="w-full py-4 text-sm font-bold text-primary bg-primary/5 hover:bg-primary/10 transition-colors flex items-center justify-center gap-2"
                onClick={handleAddTier}
              >
                <span className="material-symbols-outlined text-lg">add_circle</span>
                + Add Another Ticket Tier
              </button>
            </div>
          </section>
        )}

        <div className="pt-8 border-t border-surface-container-high flex justify-end">
          <button
            onClick={handleSave}
            disabled={submitting}
            className="px-12 py-3 rounded-lg bg-primary text-on-primary text-sm font-bold shadow-lg shadow-primary/20 hover:shadow-xl hover:-translate-y-0.5 transition-all disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {submitting ? 'Saving...' : 'Save Changes'}
          </button>
        </div>
      </div>
    </>
  );
};

export default EditEventPage;
