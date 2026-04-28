export interface EventStatus {
  eventStatus: string;
}

export interface PostalCode {
  postalCode: string;
  city: string;
}

export interface Venue {
  venueId: number;
  name: string;
  address: string;
  postalCode: PostalCode;
}

export interface Category {
  category: string;
}

export interface Event {
  eventId: number;
  title: string;
  description: string | null;
  photo: string | null;
  startTime: string;
  endTime: string;
  eventStatus: EventStatus;
  venue: Venue;
  category: Category;
}

export function formatEventDate(startTime: string): string {
  return new Date(startTime).toLocaleDateString('en-GB', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  });
}

export function formatEventLocation(venue: Venue): string {
  return `${venue.name}, ${venue.postalCode.city}`;
}
