import { useNavigate } from 'react-router-dom';
import { Event, formatEventDate, formatEventLocation } from '../types/event';
import { resolveEventImage } from '../utils/categoryImage';

interface EventCardProps {
  event: Event;
}

const EventCard = ({ event }: EventCardProps) => {
  const navigate = useNavigate();

  return (
    <div className="group bg-surface-container-lowest rounded-xl overflow-hidden shadow-sm hover:shadow-xl transition-all duration-500 border border-outline-variant/5">
      <div className="relative h-64 overflow-hidden">
        <img
          alt={event.title}
          className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700"
          src={resolveEventImage(event.photo, event.category.category)}
        />
        <div className="absolute top-4 left-4">
          <span className="bg-black/60 backdrop-blur-md text-white text-xs font-bold px-3 py-1 rounded-full uppercase tracking-widest">
            {event.category.category}
          </span>
        </div>
      </div>

      <div className="p-8">
        <h3 className="text-2xl font-bold tracking-tight text-on-surface mb-4">
          {event.title}
        </h3>

        <div className="space-y-2 mb-8">
          <div className="flex items-center gap-2 text-on-surface-variant text-sm">
            <span className="material-symbols-outlined text-sm">calendar_today</span>
            {formatEventDate(event.startTime)}
          </div>
          <div className="flex items-center gap-2 text-on-surface-variant text-sm">
            <span className="material-symbols-outlined text-sm">location_on</span>
            {formatEventLocation(event.venue)}
          </div>
        </div>

        <button
          onClick={() => { navigate(`/events/${event.eventId}`); window.scrollTo(0, 0); }}
          className="w-full py-3 bg-surface-container-high text-on-surface font-bold rounded-lg group-hover:bg-primary group-hover:text-on-primary transition-colors active:scale-[0.98]"
        >
          Find Tickets
        </button>
      </div>
    </div>
  );
};

export default EventCard;
