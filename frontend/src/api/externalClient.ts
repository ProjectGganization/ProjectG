import config from '../config/env';

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const response = await fetch(`${config.externalApiUrl}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });

  if (!response.ok) {
    throw new Error(`External API error ${response.status}: ${response.statusText}`);
  }

  return response.json() as Promise<T>;
}

const externalClient = {
  get: <T>(path: string) => request<T>(path),
  put: <T>(path: string, body?: unknown) =>
    request<T>(path, { method: 'PUT', body: body ? JSON.stringify(body) : undefined }),
};

export default externalClient;
