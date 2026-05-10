import { useEffect, useState } from "react";

interface SalesRow {
    tapahtuma: string;
    alkuaika: string;
    lipputyyppi: string;
    kpl: number;
    yhteensa: number;
}

export default function SalesReportsPage() {
    const [data, setData] = useState<SalesRow[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        fetch('/api/myyntiraportti')
            .then((res) => {
                if (!res.ok) throw new Error('Failed to fetch sales report');
                return res.json();
            })
            .then((data) => {
                setData(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []);

    const totalTickets = data.reduce((sum, row) => sum + row.kpl, 0);
    const totalRevenue = data.reduce((sum, row) => sum + row.yhteensa, 0);

    const formatDateTime = (dateStr: string) => {
        const d = new Date(dateStr);
        return d.toLocaleDateString('fi-FI', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
        });
    };

    return (
        <div className="p-6 max-w-7xl mx-auto">
            <div className="mb-6">
                <h1 className="text-2xl font-bold text-gray-900">Sales Reports</h1>
                <p className="text-sm text-gray-500 mt-1">Summary of ticket sales by event and type</p>
            </div>

            {loading && (
                <div className="text-center py-20 text-gray-400 text-sm bg-white rounded-xl border border-gray-100 shadow-sm">
                    Loading sales report...
                </div>
            )}
            {error && (
                <div className="text-center py-20 text-red-500 text-sm bg-white rounded-xl border border-gray-100 shadow-sm">
                    {error}
                </div>
            )}

            {!loading && !error && (
                <div className="bg-white rounded-xl border border-gray-200 overflow-hidden shadow-sm">
                    <table className="w-full text-sm text-left">
                        <thead className="bg-gray-50 border-b border-gray-200 text-gray-600">
                            <tr>
                                <th className="px-6 py-4 font-medium">Event</th>
                                <th className="px-6 py-4 font-medium">Ticket Type</th>
                                <th className="px-6 py-4 font-medium text-center">Qty</th>
                                <th className="px-6 py-4 font-medium text-right">Total</th>
                            </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-100">
                            {data.length === 0 ? (
                                <tr>
                                    <td colSpan={4} className="text-center py-10 text-gray-400">
                                        No sales data available.
                                    </td>
                                </tr>
                            ) : (
                                data.map((row, idx) => (
                                    <tr key={idx} className="hover:bg-gray-50 transition-colors">
                                        <td className="px-6 py-4">
                                            <div className="font-medium text-gray-900">{row.tapahtuma}</div>
                                            <div className="text-xs text-gray-400">{formatDateTime(row.alkuaika)}</div>
                                        </td>
                                        <td className="px-6 py-4 text-gray-600">{row.lipputyyppi}</td>
                                        <td className="px-6 py-4 text-center text-gray-700 font-mono">{row.kpl}</td>
                                        <td className="px-6 py-4 text-right font-mono font-medium text-gray-900">{row.yhteensa.toFixed(2)}€</td>
                                    </tr>
                                ))
                            )}
                            {data.length > 0 && (
                                <tr className="bg-blue-50/50 font-semibold border-t-2 border-gray-100">
                                    <td className="px-6 py-4 text-blue-700" colSpan={2}>GRAND TOTAL</td>
                                    <td className="px-6 py-4 text-center text-blue-700 font-mono">{totalTickets}</td>
                                    <td className="px-6 py-4 text-right text-blue-700 text-base font-mono">{totalRevenue.toFixed(2)}€</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                    {data.length > 0 && (
                        <div className="px-6 py-3 border-t border-gray-100 text-xs text-gray-400">
                            Summary based on {data.length} different ticket types sold
                        </div>
                    )}
                </div>
            )}
        </div>
    );
}