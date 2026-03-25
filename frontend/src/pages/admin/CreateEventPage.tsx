import { useState } from 'react';
import { Link } from 'react-router-dom';
import { TicketTier } from '../../types/ticketTier';
import TicketTierRow from '../../components/admin/TicketTierRow';

const INITIAL_TIERS: TicketTier[] = [
  { id: 1, type: 'VIP Gold Pass', unitPrice: 450, inStock: 50, orderLimit: 2 },
  { id: 2, type: 'General Admission', unitPrice: 120, inStock: 1000, orderLimit: 6 },
];

const CreateEventPage = () => {
  const [tiers, setTiers] = useState<TicketTier[]>(INITIAL_TIERS);

  const handleTierChange = (id: number, field: keyof TicketTier, value: string | number) => {
    setTiers((prev) =>
      prev.map((t) => (t.id === id ? { ...t, [field]: value } : t))
    );
  };

  const handleTierDelete = (id: number) => {
    setTiers((prev) => prev.filter((t) => t.id !== id));
  };

  const handleAddTier = () => {
    const newId = tiers.length > 0 ? Math.max(...tiers.map((t) => t.id)) + 1 : 1;
    setTiers((prev) => [
      ...prev,
      { id: newId, type: 'New Ticket Type', unitPrice: 0, inStock: 0, orderLimit: 1 },
    ]);
  };

  return (
    <>
      {/* Sticky Top Header */}
      <header className="sticky top-0 z-40 flex items-center justify-between px-8 w-full h-16 bg-white/70 backdrop-blur-md border-b border-slate-200/20">
        <span className="text-lg font-semibold text-slate-900">Create New Event</span>
        <div className="flex items-center gap-4">
          <Link
            to="/"
            className="text-on-surface-variant hover:text-primary text-sm font-medium transition-colors flex items-center gap-1"
          >
            <span className="material-symbols-outlined text-sm">arrow_back</span>
            Home
          </Link>
          <button className="px-4 py-2 text-sm font-medium text-primary hover:bg-primary/5 rounded-md transition-colors">
            Save Draft
          </button>
          <button className="px-6 py-2 text-sm font-semibold text-on-primary bg-gradient-to-br from-primary to-primary-container rounded-md shadow-sm hover:opacity-90 active:scale-95 transition-all">
            Publish Event
          </button>
          <div className="h-6 w-px bg-outline-variant/30 mx-2" />
          <button className="text-on-surface-variant hover:text-primary transition-colors">
            <span className="material-symbols-outlined">notifications</span>
          </button>
          <button className="text-on-surface-variant hover:text-primary transition-colors">
            <span className="material-symbols-outlined">help_outline</span>
          </button>
        </div>
      </header>

      {/* Form Content */}
      <div className="max-w-5xl mx-auto p-8 space-y-12">

        {/* Section 1: Event Information */}
        <section className="space-y-6">
          <div className="flex items-baseline gap-4">
            <h2 className="text-2xl font-bold tracking-tight text-on-surface">Event Information</h2>
            <span className="h-px flex-1 bg-surface-container-high" />
          </div>

          <div className="grid grid-cols-1 gap-8">
            <div className="space-y-2">
              <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                Event Title
              </label>
              <input
                className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-xl px-6 py-4 text-xl font-medium focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all placeholder:text-outline/50 outline-none"
                placeholder="e.g. Midnight Symphony at the Grand Hall"
                type="text"
              />
            </div>

            <div className="grid grid-cols-3 gap-8">
              {/* Description */}
              <div className="col-span-2 space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  Description
                </label>
                <div className="rounded-xl border border-outline-variant/20 bg-surface-container-lowest overflow-hidden">
                  <div className="flex items-center gap-1 p-2 border-b border-surface-container-low bg-surface-container-low/30">
                    {['format_bold', 'format_italic', 'format_list_bulleted', 'link'].map((icon) => (
                      <button key={icon} className="p-2 hover:bg-white rounded text-on-surface-variant">
                        <span className="material-symbols-outlined text-sm">{icon}</span>
                      </button>
                    ))}
                  </div>
                  <textarea
                    className="w-full border-none bg-transparent p-6 focus:ring-0 resize-none text-base outline-none"
                    placeholder="Describe the experience..."
                    rows={6}
                  />
                </div>
              </div>

              {/* Cover Media */}
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  Cover Media
                </label>
                <div className="relative group h-[228px] rounded-xl border-2 border-dashed border-outline-variant/30 bg-surface-container-low flex flex-col items-center justify-center text-center p-6 hover:bg-surface-container-high transition-colors cursor-pointer">
                  <span className="material-symbols-outlined text-4xl text-primary mb-2">add_a_photo</span>
                  <p className="text-sm font-semibold text-on-surface">Drag &amp; Drop Image</p>
                  <p className="text-[10px] text-on-surface-variant mt-1">16:9 aspect ratio recommended</p>
                </div>
              </div>
            </div>
          </div>
        </section>

        {/* Section 2 & 3: Schedule & Classification */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-12">
          {/* Schedule */}
          <section className="space-y-6">
            <div className="flex items-baseline gap-4">
              <h2 className="text-xl font-bold tracking-tight text-on-surface">Schedule</h2>
              <span className="h-px flex-1 bg-surface-container-high" />
            </div>
            <div className="grid grid-cols-2 gap-4">
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  Start Date &amp; Time
                </label>
                <input
                  className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                  type="datetime-local"
                />
              </div>
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  End Date &amp; Time
                </label>
                <input
                  className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                  type="datetime-local"
                />
              </div>
            </div>
          </section>

          {/* Classification */}
          <section className="space-y-6">
            <div className="flex items-baseline gap-4">
              <h2 className="text-xl font-bold tracking-tight text-on-surface">Classification</h2>
              <span className="h-px flex-1 bg-surface-container-high" />
            </div>
            <div className="grid grid-cols-2 gap-4">
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  Category
                </label>
                <select className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none">
                  <option>Music</option>
                  <option>Art &amp; Gallery</option>
                  <option>Professional Sports</option>
                  <option>Theater</option>
                  <option>Fine Dining</option>
                </select>
              </div>
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  Event Status
                </label>
                <select className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none">
                  <option>Draft</option>
                  <option>Published</option>
                  <option>Private / Invite Only</option>
                </select>
              </div>
            </div>
          </section>
        </div>

        {/* Section 4: Location */}
        <section className="space-y-6">
          <div className="flex items-baseline gap-4">
            <h2 className="text-xl font-bold tracking-tight text-on-surface">Location</h2>
            <span className="h-px flex-1 bg-surface-container-high" />
          </div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8 items-start">
            <div className="md:col-span-2 space-y-4">
              <div className="space-y-2">
                <label className="text-xs font-bold uppercase tracking-wider text-on-surface-variant">
                  Venue
                </label>
                <div className="flex gap-2">
                  <select className="flex-1 bg-surface-container-lowest border border-outline-variant/20 rounded-lg px-4 py-3 text-sm focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none">
                    <option>Royal Albert Hall, London</option>
                    <option>Madison Square Garden, NY</option>
                    <option>The Opera House, Sydney</option>
                    <option>+ Add New Venue</option>
                  </select>
                  <button className="px-4 py-3 bg-secondary-container text-on-secondary-container rounded-lg hover:bg-secondary-container/80 transition-colors">
                    <span className="material-symbols-outlined align-middle text-lg">add</span>
                  </button>
                </div>
              </div>
              <div className="p-4 bg-surface-container-low rounded-xl flex items-start gap-4">
                <span className="material-symbols-outlined text-primary">info</span>
                <div className="text-xs text-on-surface-variant leading-relaxed">
                  <p className="font-bold text-on-surface">Venue Capacity Auto-Limit</p>
                  <p>The selected venue has a maximum capacity of 5,272. Ticket tiers will be restricted to this total.</p>
                </div>
              </div>
            </div>
            <div className="h-40 rounded-xl bg-surface-container-highest overflow-hidden grayscale contrast-125 border border-outline-variant/10">
              <img
                alt="Venue Location Map"
                className="w-full h-full object-cover"
                src="https://picsum.photos/seed/venuemap/300/160"
              />
            </div>
          </div>
        </section>

        {/* Section 5: Ticket Tiers */}
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
                  <th className="px-6 py-4 text-[10px] font-bold uppercase tracking-widest text-on-surface-variant text-right">Unit Price ($)</th>
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

        {/* Final Action Row */}
        <div className="pt-8 border-t border-surface-container-high flex justify-between items-center">
          <div className="text-xs text-on-surface-variant flex items-center gap-2">
            <span className="w-2 h-2 rounded-full bg-primary animate-pulse" />
            Autosaved at 14:23 PM
          </div>
          <div className="flex gap-4">
            <button className="px-8 py-3 rounded-lg border border-outline-variant/30 text-sm font-semibold text-on-surface hover:bg-surface-container-low transition-colors">
              Preview Draft
            </button>
            <button className="px-12 py-3 rounded-lg bg-primary text-on-primary text-sm font-bold shadow-lg shadow-primary/20 hover:shadow-xl hover:-translate-y-0.5 transition-all">
              Launch Experience
            </button>
          </div>
        </div>

      </div>
    </>
  );
};

export default CreateEventPage;
