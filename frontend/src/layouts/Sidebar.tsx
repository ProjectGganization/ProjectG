import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { useState } from 'react';

const navItems = [
  { icon: 'home', label: 'Home', path: '/' },
  { icon: 'dashboard', label: 'Dashboard', path: '/admin' },
  { icon: 'calendar_today', label: 'Events', path: '/admin/events/create' },
  {
    icon: 'confirmation_number', label: 'Orders', path: '/admin/orders',
    children: [
      { label: 'Transactions', path: '/admin/orders' },
      { label: 'Sales Reports', path: '/admin/reports' },
    ]
  },
  { icon: 'location_on', label: 'Venues', path: '/admin/venues' },
  { icon: 'qr_code_scanner', label: 'Ticket Fetcher', path: '/admin/ticket-fetcher' },
];

const Sidebar = () => {
  const { pathname } = useLocation();
  const { user } = useAuth();
  const [ordersOpen, setOrdersOpen] = useState(pathname.includes('/admin/orders') || pathname.includes('/admin/reports'));

  return (
    <aside className="fixed left-0 top-0 h-screen w-64 flex flex-col p-4 bg-slate-50 border-r border-slate-200/50 z-50">
      <div className="mb-8 px-4">
        <h1 className="text-xl font-bold tracking-tighter text-blue-700">Precision Admin</h1>
        <p className="text-xs text-on-surface-variant font-medium">Concierge Level Control</p>
      </div>

      <nav className="flex-1 space-y-1">
        {navItems.map((item) => (
          <div key={item.label}>
            {item.children ? (
              <>
                <button
                  onClick={() => setOrdersOpen(!ordersOpen)}
                  className={`w-full flex items-center justify-between px-4 py-3 rounded-lg text-sm tracking-tight transition-colors duration-200 ${pathname.includes(item.path) || pathname.includes('/admin/reports')
                    ? 'text-blue-700 font-semibold'
                    : 'text-slate-500 hover:text-slate-900 hover:bg-slate-200/50'
                    }`}
                >
                  <div className="flex items-center gap-3">
                    <span className="material-symbols-outlined">{item.icon}</span>
                    {item.label}
                  </div>
                  <span className={`material-symbols-outlined text-xs transition-transform ${ordersOpen ? 'rotate-180' : ''}`}>
                    expand_more
                  </span>
                </button>
                {ordersOpen && (
                  <div className='ml-9 mt-1 space-y-1'>
                    {item.children.map((child) => (
                      <Link
                        key={child.path}
                        to={child.path}
                        className={`block px-4 py-2 rounded-md text-xs font-medium tracking-tight transition-colors ${pathname === child.path
                          ? 'bg-blue-100 text-blue-700'
                          : 'text-slate-500 hover:text-slate-900 hover:bg-slate-200/50'
                          }`}
                      >
                        {child.label}
                      </Link>
                    ))}
                  </div>
                )}
              </>
            ) : (
              <Link
                key={item.label}
                to={item.path}
                className={
                  pathname === item.path
                    ? 'flex items-center gap-3 px-4 py-3 bg-blue-50 text-blue-700 rounded-lg font-semibold text-sm tracking-tight'
                    : 'flex items-center gap-3 px-4 py-3 text-slate-500 hover:text-slate-900 hover:bg-slate-200/50 transition-colors duration-200 text-sm font-medium tracking-tight'
                }
              >
                <span className="material-symbols-outlined">{item.icon}</span>
                {item.label}
              </Link>
            )}
          </div>
        ))}

      </nav >

      <div className="mt-auto p-4 bg-slate-100 rounded-xl">
        <div className="flex items-center gap-3">
          <div className="w-8 h-8 rounded-full bg-blue-100 flex items-center justify-center flex-shrink-0">
            <span className="text-xs font-bold text-blue-700">
              {user?.email?.charAt(0).toUpperCase() ?? '?'}
            </span>
          </div>
          <div className="min-w-0">
            <p className="text-xs font-bold text-on-surface truncate">{user?.email ?? 'Not signed in'}</p>
            <p className="text-[10px] text-on-surface-variant">Admin</p>
          </div>
        </div>
      </div>
    </aside >
  );
};

export default Sidebar;
