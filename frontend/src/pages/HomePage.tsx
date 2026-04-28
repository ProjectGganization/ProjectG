import { useEffect, useState } from 'react';
import EventCard from '../components/EventCard';
import { Event } from '../types/event';
import { getEvents } from '../api/eventService';

const HomePage = () => {
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    getEvents()
      .then(setEvents)
      .catch(() => setError('Could not load events. Make sure the backend is running.'))
      .finally(() => setLoading(false));
  }, []);

  return (
    <>
      {/* Hero Section */}
      <section className="relative h-[819px] w-full overflow-hidden">
        <div className="absolute inset-0">
          <img
            alt="Summer Music Festival"
            className="w-full h-full object-cover"
            src="https://picsum.photos/seed/festival/1920/819"
          />
          <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent" />
        </div>
        <div className="relative z-10 h-full max-w-screen-2xl mx-auto px-6 flex flex-col justify-end pb-24">
          <span className="text-primary-container font-semibold tracking-widest uppercase text-sm mb-4">
            Featured Experience
          </span>
          <h1 className="text-white text-6xl md:text-8xl font-black tracking-tighter mb-8 max-w-4xl leading-tight">
            Summer Music Festival
          </h1>
          <div className="flex gap-4">
            <button className="signature-gradient text-on-primary px-8 py-4 rounded-lg text-lg font-bold active:scale-95 transition-transform flex items-center gap-2">
              Buy Tickets
              <span className="material-symbols-outlined">arrow_forward</span>
            </button>
          </div>
        </div>
      </section>

      {/* About Us Section */}
      <section className="py-24 bg-surface">
        <div className="max-w-screen-2xl mx-auto px-6">
          <div className="grid md:grid-cols-2 gap-16 items-center">
            <div>
              <h2 className="text-4xl md:text-5xl font-black tracking-tighter text-on-surface mb-8">
                Who We Are
              </h2>
              <div className="space-y-6 text-lg text-on-surface-variant leading-relaxed font-light">
                <p>
                  ProjectG is a full-stack event ticketing platform built by a team of students
                  as part of a software engineering project. We set out to design and build a
                  real-world system — from database to UI — that handles the complete lifecycle
                  of event ticketing.
                </p>
                <p>
                  The platform supports customers browsing and purchasing tickets, sellers
                  processing sales at the counter, and administrators managing the system.
                  Every order is fully traceable, every role carefully scoped — built the
                  way a production system should be.
                </p>
              </div>
            </div>
            <div className="relative aspect-video rounded-xl overflow-hidden bg-surface-container-low shadow-2xl">
              <img
                alt="Professional Team"
                className="w-full h-full object-cover grayscale hover:grayscale-0 transition-all duration-700"
                src="https://picsum.photos/seed/teamphoto/800/450"
              />
            </div>
          </div>
        </div>
      </section>

      {/* Upcoming Events Section */}
      <section className="py-24 bg-surface-container-low">
        <div className="max-w-screen-2xl mx-auto px-6">
          <div className="flex justify-between items-end mb-16">
            <div>
              <h2 className="text-4xl font-black tracking-tighter text-on-surface">
                Upcoming Events
              </h2>
              <p className="text-on-surface-variant mt-2">
                Hand-picked experiences for the discerning enthusiast.
              </p>
            </div>
            <button className="text-primary font-bold flex items-center gap-2 hover:underline underline-offset-8 transition-all">
              View All Events
              <span className="material-symbols-outlined">arrow_right_alt</span>
            </button>
          </div>

          {loading && (
            <div className="flex justify-center py-24">
              <span className="material-symbols-outlined animate-spin text-primary text-4xl">progress_activity</span>
            </div>
          )}

          {error && (
            <div className="text-center py-24 text-on-surface-variant">
              <span className="material-symbols-outlined text-4xl mb-4 block">error</span>
              {error}
            </div>
          )}

          {!loading && !error && events.length === 0 && (
            <div className="text-center py-24 text-on-surface-variant">
              No events found.
            </div>
          )}

          {!loading && !error && events.length > 0 && (
            <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
              {events.map((event) => (
                <EventCard key={event.eventId} event={event} />
              ))}
            </div>
          )}
        </div>
      </section>
    </>
  );
};

export default HomePage;
