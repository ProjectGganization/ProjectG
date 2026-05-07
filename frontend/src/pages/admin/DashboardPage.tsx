import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getEvents } from '../../api/eventService';
import { Event, formatEventDate } from '../../types/event';

const DashboardPage = () => {
  const navigate = useNavigate();
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getEvents()
      .then(setEvents)
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  const upcomingEvents = events
    .filter((e) => e.eventStatus.eventStatus === 'upcoming')
    .sort((a, b) => new Date(a.startTime).getTime() - new Date(b.startTime).getTime())
    .slice(0, 5);

  const stats = [
    {
      label: 'Total Events',
      value: events.length,
      icon: 'calendar_today',
      sub: `${upcomingEvents.length} upcoming`,
    },
    {
      label: 'Active Venues',
      value: [...new Set(events.map((e) => e.venue.venueId))].length,
      icon: 'location_on',
      sub: 'venues in use',
    },
    {
      label: 'Categories',
      value: [...new Set(events.map((e) => e.category.category))].length,
      icon: 'label',
      sub: 'event categories',
    },
  ];

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
        <span className="text-lg font-semibold text-slate-900">Dashboard</span>
        <Link
          to="/admin/events/create"
          className="px-5 py-2 text-sm font-semibold text-on-primary bg-gradient-to-br from-primary to-primary-container rounded-md shadow-sm hover:opacity-90 active:scale-95 transition-all"
        >
          + New Event
        </Link>
      </header>

      <div className="max-w-5xl mx-auto p-8 space-y-10">

        {/* Stat cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {stats.map((stat) => (
            <div key={stat.label} className="bg-surface-container-lowest border border-outline-variant/10 rounded-xl p-6 flex items-center gap-5">
              <div className="w-12 h-12 rounded-xl bg-primary/10 flex items-center justify-center shrink-0">
                <span className="material-symbols-outlined text-primary">{stat.icon}</span>
              </div>
              <div>
                <p className="text-3xl font-black tracking-tight text-on-surface">{stat.value}</p>
                <p className="text-sm font-semibold text-on-surface">{stat.label}</p>
                <p className="text-xs text-on-surface-variant mt-0.5">{stat.sub}</p>
              </div>
            </div>
          ))}
        </div>

        {/* Upcoming Events */}
        <section className="space-y-4">
          <div className="flex items-baseline gap-4">
            <h2 className="text-base font-bold text-on-surface">Upcoming Events</h2>
            <span className="h-px flex-1 bg-surface-container-high" />
            <Link to="/admin/events/create" className="text-xs font-semibold text-primary hover:underline">
              + New event
            </Link>
          </div>
          <div className="bg-surface-container-lowest border border-outline-variant/10 rounded-xl divide-y divide-surface-container-low overflow-hidden">
            {upcomingEvents.length === 0 ? (
              <p className="text-sm text-on-surface-variant text-center py-10">No upcoming events.</p>
            ) : (
              upcomingEvents.map((event) => (
                <div
                  key={event.eventId}
                  onClick={() => navigate(`/admin/events/${event.eventId}/edit`)}
                  className="flex items-center justify-between px-5 py-4 hover:bg-surface-container-low transition-colors cursor-pointer"
                >
                  <div className="min-w-0">
                    <p className="text-sm font-semibold text-on-surface truncate">{event.title}</p>
                    <p className="text-xs text-on-surface-variant mt-0.5">
                      {formatEventDate(event.startTime)} · {event.venue.name}, {event.venue.postalCode.city}
                    </p>
                  </div>
                  <span className="ml-4 shrink-0 text-[10px] font-bold px-2 py-1 rounded-full bg-secondary-container text-on-secondary-container uppercase tracking-wide">
                    {event.category.category}
                  </span>
                </div>
              ))
            )}
          </div>
        </section>

      </div>
    </>
  );
};

export default DashboardPage;
