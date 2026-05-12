import { useEffect, useRef, useState } from 'react';
import { Venue } from '../../types/event';
import { getVenues, createVenue, updateVenue } from '../../api/venueService';
import { getPostalCode, createPostalCode } from '../../api/postalCodeService';

interface VenueForm {
  name: string;
  address: string;
  postalCode: string;
  city: string;
}

const emptyForm: VenueForm = { name: '', address: '', postalCode: '', city: '' };

export default function VenuesPage() {
  const [venues, setVenues] = useState<Venue[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [showModal, setShowModal] = useState(false);
  const [editingVenue, setEditingVenue] = useState<Venue | null>(null);
  const [form, setForm] = useState<VenueForm>(emptyForm);
  const [submitting, setSubmitting] = useState(false);
  const [formError, setFormError] = useState<string | null>(null);

  // postal code lookup state
  const [postalLookup, setPostalLookup] = useState<'idle' | 'loading' | 'found' | 'notfound'>('idle');
  const postalDebounce = useRef<ReturnType<typeof setTimeout> | null>(null);

  const [deletedIds, setDeletedIds] = useState<Set<number>>(new Set());

  useEffect(() => {
    loadVenues();
  }, []);

  function loadVenues() {
    setLoading(true);
    getVenues()
      .then((data) => { setVenues(data); setLoading(false); })
      .catch((err) => { setError(err.message); setLoading(false); });
  }

  function openCreate() {
    setEditingVenue(null);
    setForm(emptyForm);
    setFormError(null);
    setPostalLookup('idle');
    setShowModal(true);
  }

  function openEdit(venue: Venue) {
    setEditingVenue(venue);
    setForm({
      name: venue.name,
      address: venue.address,
      postalCode: venue.postalCode.postalCode,
      city: venue.postalCode.city,
    });
    setFormError(null);
    setPostalLookup('found');
    setShowModal(true);
  }

  function handlePostalCodeChange(value: string) {
    setForm((prev) => ({ ...prev, postalCode: value, city: '' }));
    setPostalLookup('idle');

    if (postalDebounce.current) clearTimeout(postalDebounce.current);
    if (value.length !== 5) return;

    setPostalLookup('loading');
    postalDebounce.current = setTimeout(async () => {
      try {
        const result = await getPostalCode(value);
        setForm((prev) => ({ ...prev, city: result.city }));
        setPostalLookup('found');
      } catch {
        setPostalLookup('notfound');
      }
    }, 400);
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!form.name.trim() || !form.address.trim() || !form.postalCode.trim()) {
      setFormError('Name, address and postal code are required.');
      return;
    }
    if (postalLookup === 'notfound' && !form.city.trim()) {
      setFormError('Enter a city for the new postal code.');
      return;
    }
    setSubmitting(true);
    setFormError(null);
    try {
      if (postalLookup === 'notfound') {
        await createPostalCode(form.postalCode, form.city);
      }
      if (editingVenue) {
        const updated = await updateVenue(editingVenue.venueId, form);
        setVenues((prev) => prev.map((v) => v.venueId === updated.venueId ? updated : v));
      } else {
        const created = await createVenue(form);
        setVenues((prev) => [...prev, created]);
      }
      setShowModal(false);
    } catch (err: unknown) {
      setFormError(err instanceof Error ? err.message : 'Save failed.');
    } finally {
      setSubmitting(false);
    }
  }

  function handleDelete(id: number) {
    setDeletedIds((prev) => {
      const next = new Set(prev);
      if (next.has(id)) {
        next.delete(id);
      } else {
        next.add(id);
      }
      return next;
    });
  }

  return (
    <div className="p-6 max-w-4xl mx-auto">
      <div className="mb-6 flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Venues</h1>
          <p className="text-sm text-gray-500 mt-1">Manage venues</p>
        </div>
        <button
          onClick={openCreate}
          className="flex items-center gap-2 px-4 py-2 bg-blue-600 text-white text-sm font-medium rounded-lg hover:bg-blue-700 transition-colors"
        >
          <span className="material-symbols-outlined text-base">add</span>
          Add venue
        </button>
      </div>

      {loading && <div className="text-center py-20 text-gray-400 text-sm">Loading...</div>}
      {error && <div className="text-center py-20 text-red-500 text-sm">{error}</div>}

      {!loading && !error && (
        <div className="bg-white rounded-xl border border-gray-200 overflow-hidden shadow-sm">
          <table className="w-full text-sm">
            <thead className="bg-gray-50 border-b border-gray-200">
              <tr>
                <th className="text-left px-4 py-3 font-medium text-gray-600">#</th>
                <th className="text-left px-4 py-3 font-medium text-gray-600">Name</th>
                <th className="text-left px-4 py-3 font-medium text-gray-600">Address</th>
                <th className="text-left px-4 py-3 font-medium text-gray-600">Postal code</th>
                <th className="text-left px-4 py-3 font-medium text-gray-600">City</th>
                <th className="px-4 py-3" />
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100">
              {venues.length === 0 ? (
                <tr>
                  <td colSpan={6} className="text-center py-10 text-gray-400">
                    No venues. Add the first one.
                  </td>
                </tr>
              ) : (
                venues.map((venue) => {
                  const isDeleted = deletedIds.has(venue.venueId);
                  return (
                    <tr
                      key={venue.venueId}
                      className={isDeleted ? 'bg-red-50' : 'hover:bg-gray-50 transition-colors'}
                    >
                      <td className={`px-4 py-3 font-mono ${isDeleted ? 'text-red-300' : 'text-gray-500'}`}>{venue.venueId}</td>
                      <td className={`px-4 py-3 font-medium ${isDeleted ? 'text-red-400 line-through' : 'text-gray-900'}`}>
                        {venue.name}
                      </td>
                      <td className={`px-4 py-3 ${isDeleted ? 'text-red-300 line-through' : 'text-gray-600'}`}>{venue.address}</td>
                      <td className={`px-4 py-3 ${isDeleted ? 'text-red-300' : 'text-gray-600'}`}>{venue.postalCode.postalCode}</td>
                      <td className={`px-4 py-3 ${isDeleted ? 'text-red-300' : 'text-gray-600'}`}>{venue.postalCode.city}</td>
                      <td className="px-4 py-3">
                        <div className="flex items-center justify-end gap-2">
                          {isDeleted ? (
                            <span className="text-xs text-red-400 font-medium px-2 py-0.5 bg-red-100 rounded-full">Deleted</span>
                          ) : (
                            <button
                              onClick={() => openEdit(venue)}
                              className="p-1.5 text-gray-400 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
                              title="Edit"
                            >
                              <span className="material-symbols-outlined text-base">edit</span>
                            </button>
                          )}
                          <button
                            onClick={() => handleDelete(venue.venueId)}
                            className={`p-1.5 rounded-lg transition-colors ${isDeleted ? 'text-red-400 hover:text-gray-500 hover:bg-gray-100' : 'text-gray-400 hover:text-red-600 hover:bg-red-50'}`}
                            title={isDeleted ? 'Undo delete' : 'Delete'}
                          >
                            <span className="material-symbols-outlined text-base">{isDeleted ? 'undo' : 'delete'}</span>
                          </button>
                        </div>
                      </td>
                    </tr>
                  );
                })
              )}
            </tbody>
          </table>
          {venues.length > 0 && (
            <div className="px-4 py-3 border-t border-gray-100 text-xs text-gray-400">
              {venues.length} venue{venues.length !== 1 ? 's' : ''}
            </div>
          )}
        </div>
      )}

      {showModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40">
          <div className="bg-white rounded-2xl shadow-xl w-full max-w-md mx-4 p-6">
            <div className="flex items-center justify-between mb-5">
              <h2 className="text-lg font-bold text-gray-900">
                {editingVenue ? 'Edit venue' : 'Add new venue'}
              </h2>
              <button
                onClick={() => setShowModal(false)}
                className="p-1 text-gray-400 hover:text-gray-600 rounded-lg"
              >
                <span className="material-symbols-outlined">close</span>
              </button>
            </div>

            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Name</label>
                <input
                  type="text"
                  value={form.name}
                  onChange={(e) => setForm({ ...form, name: e.target.value })}
                  className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g. Hartwall Arena"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Address</label>
                <input
                  type="text"
                  value={form.address}
                  onChange={(e) => setForm({ ...form, address: e.target.value })}
                  className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="e.g. Areenankuja 1"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Postal code</label>
                <div className="relative">
                  <input
                    type="text"
                    value={form.postalCode}
                    onChange={(e) => handlePostalCodeChange(e.target.value)}
                    maxLength={5}
                    className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 pr-8"
                    placeholder="e.g. 00240"
                  />
                  {postalLookup === 'loading' && (
                    <span className="absolute right-2 top-2.5 text-gray-400 text-xs">...</span>
                  )}
                  {postalLookup === 'found' && (
                    <span className="absolute right-2 top-2 material-symbols-outlined text-green-500 text-base">check_circle</span>
                  )}
                  {postalLookup === 'notfound' && (
                    <span className="absolute right-2 top-2 material-symbols-outlined text-amber-500 text-base">warning</span>
                  )}
                </div>
                {postalLookup === 'found' && form.city && (
                  <p className="mt-1 text-xs text-green-600">{form.city}</p>
                )}
                {postalLookup === 'notfound' && (
                  <p className="mt-1 text-xs text-amber-600">Postal code not found — enter a city below</p>
                )}
              </div>

              {postalLookup === 'notfound' && (
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">City</label>
                  <input
                    type="text"
                    value={form.city}
                    onChange={(e) => setForm({ ...form, city: e.target.value })}
                    className="w-full border border-amber-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-amber-400"
                    placeholder="e.g. Helsinki"
                    autoFocus
                  />
                  <p className="mt-1 text-xs text-gray-400">The postal code will be created automatically on save.</p>
                </div>
              )}

              {formError && (
                <p className="text-sm text-red-500">{formError}</p>
              )}

              <div className="flex gap-3 pt-2">
                <button
                  type="button"
                  onClick={() => setShowModal(false)}
                  className="flex-1 px-4 py-2 border border-gray-300 text-gray-700 text-sm font-medium rounded-lg hover:bg-gray-50 transition-colors"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  disabled={submitting || postalLookup === 'loading'}
                  className="flex-1 px-4 py-2 bg-blue-600 text-white text-sm font-medium rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50"
                >
                  {submitting ? 'Saving...' : editingVenue ? 'Save' : 'Add'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}
