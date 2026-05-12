import { useState } from 'react';
import externalClient from '../../api/externalClient';

const TicketFetcherPage = () => {
  const [ticketId, setTicketId] = useState('');
  const [result, setResult] = useState<Record<string, unknown> | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [marking, setMarking] = useState(false);

  const handleFetch = async () => {
    if (!ticketId.trim()) return;
    setLoading(true);
    setError(null);
    setResult(null);
    try {
      const data = await externalClient.get<Record<string, unknown>>(`/api/inspect/${ticketId.trim()}`);
      setResult(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
    } finally {
      setLoading(false);
    }
  };

  const handleMarkUsed = async () => {
    if (!ticketId.trim()) return;
    setMarking(true);
    setError(null);
    try {
      const data = await externalClient.put<Record<string, unknown>>(`/api/inspect/${ticketId.trim()}/use`);
      setResult(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
    } finally {
      setMarking(false);
    }
  };

  return (
    <>
      {/* Sticky Top Header */}
      <header className="sticky top-0 z-40 flex items-center justify-between px-8 w-full h-16 bg-white/70 backdrop-blur-md border-b border-slate-200/20">
        <span className="text-lg font-semibold text-slate-900">Admin Fetch Tool</span>
        <div className="flex items-center gap-4">
          <button className="p-2 text-slate-500 hover:text-primary transition-colors duration-200 active:opacity-80">
            <span className="material-symbols-outlined">notifications</span>
          </button>
          <button className="p-2 text-slate-500 hover:text-primary transition-colors duration-200 active:opacity-80">
            <span className="material-symbols-outlined">account_circle</span>
          </button>
        </div>
      </header>

      {/* Main Content */}
      <div className="flex-grow flex items-center justify-center px-6 py-16">
        <div className="w-full max-w-md bg-surface-container-lowest rounded-xl shadow-sm overflow-hidden p-8 space-y-8">
          <div className="text-center space-y-2">
            <h1 className="text-2xl font-bold tracking-tight text-on-surface">Precision Fetch</h1>
            <p className="text-on-surface-variant text-sm">Enter ticket ID or scan QR code to retrieve data.</p>
          </div>

          {/* Dual Input Methods */}
          <div className="space-y-6">
            {/* QR Scan Area */}
            <div className="relative group cursor-pointer">
              <div className="w-full aspect-square rounded-xl bg-surface-container-low flex flex-col items-center justify-center border-2 border-dashed border-outline-variant/30 group-hover:border-primary/50 transition-all duration-300">
                <div className="p-6 rounded-full bg-surface-container-lowest shadow-sm mb-4">
                  <span className="material-symbols-outlined text-4xl text-primary">qr_code_scanner</span>
                </div>
                <span className="text-sm font-medium text-on-surface">Scan QR Code</span>
                <span className="text-xs text-on-surface-variant mt-1">Tap to open camera</span>
              </div>
            </div>

            <div className="relative flex items-center">
              <div className="flex-grow border-t border-outline-variant/20"></div>
              <span className="flex-shrink mx-4 text-xs font-semibold text-outline-variant tracking-widest">OR</span>
              <div className="flex-grow border-t border-outline-variant/20"></div>
            </div>

            {/* Manual Entry */}
            <div className="space-y-2">
              <label className="text-xs font-bold text-on-surface-variant tracking-wider uppercase ml-1" htmlFor="manual-id">
                Manual Entry
              </label>
              <div className="relative">
                <span className="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-outline">pin</span>
                <input
                  className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-xl py-4 pl-12 pr-4 text-on-surface placeholder:text-outline/50 focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all outline-none"
                  id="manual-id"
                  placeholder="Enter Code or ID..."
                  type="text"
                  value={ticketId}
                  onChange={(e) => setTicketId(e.target.value)}
                  onKeyDown={(e) => e.key === 'Enter' && handleFetch()}
                />
              </div>
            </div>
          </div>

          {/* Fetch Button */}
          <button
            onClick={handleFetch}
            disabled={loading || !ticketId.trim()}
            className="w-full bg-gradient-to-br from-primary to-primary-container text-on-primary font-semibold py-4 rounded-xl shadow-lg shadow-primary/20 hover:shadow-xl hover:shadow-primary/30 active:scale-[0.98] transition-all flex items-center justify-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span className="material-symbols-outlined">{loading ? 'hourglass_empty' : 'search'}</span>
            {loading ? 'Fetching...' : 'Fetch Data'}
          </button>

          {/* Result */}
          {error && (
            <div className="p-4 bg-error-container text-on-error-container rounded-xl text-sm font-medium">
              {error}
            </div>
          )}
          {result && (
            <div className="space-y-4">
              {result.used === true && (
                <div className="p-4 bg-secondary-container text-on-secondary-container rounded-xl text-sm font-semibold flex items-center gap-2">
                  <span className="material-symbols-outlined text-base">check_circle</span>
                  Ticket is now used
                </div>
              )}
              {result.used !== true && (
                <button
                  onClick={handleMarkUsed}
                  disabled={marking}
                  className="w-full bg-gradient-to-br from-primary to-primary-container text-on-primary font-semibold py-4 rounded-xl shadow-lg active:scale-[0.98] transition-all flex items-center justify-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  <span className="material-symbols-outlined">{marking ? 'hourglass_empty' : 'check_circle'}</span>
                  {marking ? 'Marking...' : 'Mark as Used'}
                </button>
              )}
            </div>
          )}

          {/* Footer info */}
          <div className="flex justify-center items-center gap-4 pt-2">
            <div className="flex items-center gap-1">
              <span className="material-symbols-outlined text-xs text-outline" style={{ fontVariationSettings: "'FILL' 1" }}>lock</span>
              <span className="text-[10px] font-bold text-outline-variant uppercase tracking-tighter">Secure Fetch</span>
            </div>
            <div className="w-1 h-1 rounded-full bg-outline-variant/30"></div>
            <div className="flex items-center gap-1">
              <span className="material-symbols-outlined text-xs text-outline">history</span>
              <span className="text-[10px] font-bold text-outline-variant uppercase tracking-tighter">View Recent</span>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default TicketFetcherPage;
