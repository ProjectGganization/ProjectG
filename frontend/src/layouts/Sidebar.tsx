const navItems = [
  { icon: 'dashboard', label: 'Dashboard', active: false },
  { icon: 'calendar_today', label: 'Events', active: true },
  { icon: 'confirmation_number', label: 'Orders', active: false },
  { icon: 'settings', label: 'Settings', active: false },
];

const Sidebar = () => {
  return (
    <aside className="fixed left-0 top-0 h-screen w-64 flex flex-col p-4 bg-slate-50 border-r border-slate-200/50 z-50">
      <div className="mb-8 px-4">
        <h1 className="text-xl font-bold tracking-tighter text-blue-700">Precision Admin</h1>
        <p className="text-xs text-on-surface-variant font-medium">Concierge Level Control</p>
      </div>

      <nav className="flex-1 space-y-1">
        {navItems.map((item) => (
          <a
            key={item.label}
            href="#"
            className={
              item.active
                ? 'flex items-center gap-3 px-4 py-3 bg-blue-50 text-blue-700 rounded-lg font-semibold text-sm tracking-tight'
                : 'flex items-center gap-3 px-4 py-3 text-slate-500 hover:text-slate-900 hover:bg-slate-200/50 transition-colors duration-200 text-sm font-medium tracking-tight'
            }
          >
            <span className="material-symbols-outlined">{item.icon}</span>
            {item.label}
          </a>
        ))}
      </nav>

      <div className="mt-auto p-4 bg-slate-100 rounded-xl">
        <div className="flex items-center gap-3">
          <img
            alt="Admin User"
            className="w-8 h-8 rounded-full bg-surface-container-highest object-cover"
            src="https://picsum.photos/seed/adminuser/32/32"
          />
          <div>
            <p className="text-xs font-bold text-on-surface">Admin User</p>
            <p className="text-[10px] text-on-surface-variant">Super Admin</p>
          </div>
        </div>
      </div>
    </aside>
  );
};

export default Sidebar;
