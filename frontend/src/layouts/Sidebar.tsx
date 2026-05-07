import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const navItems = [
  { icon: 'dashboard', label: 'Dashboard', path: '/admin' },
  { icon: 'calendar_today', label: 'Events', path: '/admin/events/create' },
  { icon: 'confirmation_number', label: 'Orders', path: '/admin/orders' },
  { icon: 'qr_code_scanner', label: 'Ticket Fetcher', path: '/admin/ticket-fetcher' },
];

const bottomItems = [
  { icon: 'home', label: 'Home', path: '/' },
];

const Sidebar = () => {
  const { pathname } = useLocation();
  const { user } = useAuth();

  return (
    <aside className="fixed left-0 top-0 h-screen w-64 flex flex-col p-4 bg-slate-50 border-r border-slate-200/50 z-50">
      <div className="mb-8 px-4">
        <h1 className="text-xl font-bold tracking-tighter text-blue-700">Precision Admin</h1>
        <p className="text-xs text-on-surface-variant font-medium">Concierge Level Control</p>
      </div>

      <nav className="flex-1 space-y-1">
        {navItems.map((item) => (
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
        ))}
        <div className="pt-2 border-t border-slate-200/60 mt-2">
          {bottomItems.map((item) => (
            <Link
              key={item.label}
              to={item.path}
              className="flex items-center gap-3 px-4 py-3 text-slate-500 hover:text-slate-900 hover:bg-slate-200/50 transition-colors duration-200 text-sm font-medium tracking-tight"
            >
              <span className="material-symbols-outlined">{item.icon}</span>
              {item.label}
            </Link>
          ))}
        </div>
      </nav>

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
    </aside>
  );
};

export default Sidebar;
