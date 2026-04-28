
import { Link, useNavigate } from 'react-router-dom';


const Navbar = () => {
  const navigate = useNavigate();
  return (
    <nav className="fixed top-0 w-full z-50 glass-nav shadow-sm font-inter antialiased tracking-tight bg-red-500">
      <div className="flex justify-between items-center px-6 py-4 max-w-screen-2xl mx-auto w-full">
        <div className="flex items-center gap-8">
          <span className="text-2xl font-black tracking-tighter text-slate-900">
            TicketGG
          </span>
          <div className="hidden md:flex gap-6">
            <a
              className="text-blue-600 font-semibold border-b-2 border-blue-600 transition-all duration-300"
              href="#"
            >
              Events
            </a>
          </div>
        </div>

        <div className="flex items-center gap-4 flex-1 justify-end max-w-md ml-auto mr-8">
          <div className="relative w-full group">
            <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-outline-variant group-focus-within:text-primary">
              search
            </span>
            <input
              className="w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg pl-10 pr-4 py-2 focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all duration-300 outline-none"
              placeholder="Search curated experiences..."
              type="text"
            />
          </div>
        </div>

        <div className="flex items-center gap-3">
          <Link
            to="/admin/events/create"
            className="text-on-surface-variant hover:text-primary text-sm font-medium transition-colors"
          >
            Admin
          </Link>
          
          <button
           onClick={() => navigate('/signin')} 
          className="bg-primary text-on-primary px-5 py-2 rounded-lg font-semibold signature-gradient active:scale-95 transition-transform hover:opacity-90">
            Sign In
          </button>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
