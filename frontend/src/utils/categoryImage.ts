// Maps event category keywords to relevant Unsplash placeholder images.
// Used when an event has no photo set in the database.

const CATEGORY_IMAGES: { keywords: string[]; url: string }[] = [
  {
    keywords: ['rock', 'metal', 'punk', 'alternative', 'indie'],
    url: 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['jazz', 'blues', 'swing', 'bebop'],
    url: 'https://images.unsplash.com/photo-1415201364774-f6f0bb35f28f?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['classical', 'orchestra', 'symphony', 'opera', 'chamber', 'piano', 'violin'],
    url: 'https://images.unsplash.com/photo-1507838153414-b4b713384a76?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['electronic', 'edm', 'techno', 'house', 'trance', 'rave', 'dj'],
    url: 'https://images.unsplash.com/photo-1571397133301-523cf34b7b9f?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['hip hop', 'hiphop', 'rap', 'r&b', 'rnb'],
    url: 'https://images.unsplash.com/photo-1598387993281-cecf8b71a8f8?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['pop', 'dance', 'chart'],
    url: 'https://images.unsplash.com/photo-1501386761578-eaa54b0baa24?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['festival', 'outdoor', 'summer', 'open air'],
    url: 'https://images.unsplash.com/photo-1533174072545-7a4b6ad7a6c3?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['sport', 'football', 'basketball', 'hockey', 'tennis', 'esport', 'gaming'],
    url: 'https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['art', 'exhibition', 'gallery', 'museum', 'expo'],
    url: 'https://images.unsplash.com/photo-1531243269054-5ebf6f34081e?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['comedy', 'stand-up', 'standup', 'theater', 'theatre', 'show'],
    url: 'https://images.unsplash.com/photo-1516280440614-37939bbacd81?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['food', 'drink', 'wine', 'beer', 'gourmet', 'tasting'],
    url: 'https://images.unsplash.com/photo-1414235077428-338989a2e8c0?w=800&h=500&fit=crop&auto=format',
  },
  {
    keywords: ['conference', 'seminar', 'workshop', 'talk', 'lecture', 'networking'],
    url: 'https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=800&h=500&fit=crop&auto=format',
  },
];

const FALLBACK_IMAGE =
  'https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=800&h=500&fit=crop&auto=format';

export function getCategoryImage(category: string): string {
  const normalized = category.toLowerCase();
  const match = CATEGORY_IMAGES.find(({ keywords }) =>
    keywords.some((kw) => normalized.includes(kw))
  );
  return match?.url ?? FALLBACK_IMAGE;
}

/** Returns photo URL if it's a valid http/https URL, otherwise falls back to category image. */
export function resolveEventImage(photo: string | null, category: string): string {
  if (photo && (photo.startsWith('http://') || photo.startsWith('https://'))) {
    return photo;
  }
  return getCategoryImage(category);
}
