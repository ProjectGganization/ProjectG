import config from '../config/env';

async function get<T>(path: string): Promise<T> {
  const response = await fetch(`${config.externalApiUrl}${path}`, {
    headers: { 'Content-Type': 'application/json' },
  });

  if (!response.ok) {
    throw new Error(`External API error ${response.status}: ${response.statusText}`);
  }

  return response.json() as Promise<T>;
}

const externalClient = { get };

export default externalClient;
