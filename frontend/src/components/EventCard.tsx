import { Event } from '../types/event';

interface EventCardProps {
  event: Event;
}

const EventCard = ({ event }: EventCardProps) => {
  return (
    <div className="group bg-surface-container-lowest rounded-xl overflow-hidden shadow-sm hover:shadow-xl transition-all duration-500 border border-outline-variant/5">
      <div className="relative h-64 overflow-hidden">
        <img
          alt={event.title}
          className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700"
          src={event.imageUrl}
        />
        <div className="absolute top-4 left-4">
          <span className="bg-black/60 backdrop-blur-md text-white text-xs font-bold px-3 py-1 rounded-full uppercase tracking-widest">
            {event.category}
          </span>
        </div>
      </div>

      <div className="p-8">
        <div className="flex justify-between items-start mb-4">
          <h3 className="text-2xl font-bold tracking-tight text-on-surface">{event.title}</h3>
          <div className="text-right">
            <span className="block text-xs text-outline font-bold uppercase tracking-widest">From</span>
            <span className="text-xl font-black text-primary">${event.price}</span>
          </div>
        </div>

        <div className="space-y-2 mb-8">
          <div className="flex items-center gap-2 text-on-surface-variant text-sm">
            <span className="material-symbols-outlined text-sm">calendar_today</span>
            {event.date}
          </div>
          <div className="flex items-center gap-2 text-on-surface-variant text-sm">
            <span className="material-symbols-outlined text-sm">location_on</span>
            {event.location}
          </div>
        </div>

        <button className="w-full py-3 bg-surface-container-high text-on-surface font-bold rounded-lg group-hover:bg-primary group-hover:text-on-primary transition-colors active:scale-[0.98]">
          Secure Selection
        </button>
      </div>
    </div>
  );
};

export default EventCard;
