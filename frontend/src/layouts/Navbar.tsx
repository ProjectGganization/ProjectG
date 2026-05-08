import {
  Link,
  useNavigate,
  useLocation,
  useSearchParams,
} from "react-router-dom";
import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthContext";

const Navbar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();
  const { user, logout } = useAuth();

  const [query, setQuery] = useState(searchParams.get("q") ?? "");

  useEffect(() => {
    if (location.pathname === "/") {
      setQuery(searchParams.get("q") ?? "");
    }
  }, [location.pathname, searchParams]);

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setQuery(value);
    if (location.pathname === "/") {
      navigate(value ? `/?q=${encodeURIComponent(value)}` : "/", {
        replace: true,
      });
    }
  };

  const handleSearchKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      navigate(query ? `/?q=${encodeURIComponent(query)}` : "/");
      setTimeout(
        () =>
          document
            .getElementById("events")
            ?.scrollIntoView({ behavior: "smooth" }),
        100,
      );
    }
  };

  const scrollToEvents = () => {
    if (location.pathname === "/") {
      document.getElementById("events")?.scrollIntoView({ behavior: "smooth" });
    } else {
      navigate("/");
      setTimeout(
        () =>
          document
            .getElementById("events")
            ?.scrollIntoView({ behavior: "smooth" }),
        100,
      );
    }
  };
  return (
    <nav className='fixed top-0 w-full z-50 glass-nav shadow-sm font-inter antialiased tracking-tight'>
      <div className='flex justify-between items-center px-6 py-4 max-w-screen-2xl mx-auto w-full'>
        <div className='flex items-center gap-8'>
          <span className='text-2xl font-black tracking-tighter text-slate-900'>
            TicketGG
          </span>
          <div className='hidden md:flex gap-6'>
            <button
              className='text-blue-600 font-semibold border-b-2 border-blue-600 transition-all duration-300'
              onClick={scrollToEvents}
            >
              Events
            </button>
          </div>
        </div>

        <div className='flex items-center gap-4 flex-1 justify-end max-w-md ml-auto mr-8'>
          <div className='relative w-full group'>
            <span className='material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-outline-variant group-focus-within:text-primary'>
              search
            </span>
            <input
              className='w-full bg-surface-container-lowest border border-outline-variant/20 rounded-lg pl-10 pr-4 py-2 focus:ring-2 focus:ring-primary/10 focus:border-primary transition-all duration-300 outline-none'
              placeholder='Search curated experiences...'
              type='text'
              value={query}
              onChange={handleSearchChange}
              onKeyDown={handleSearchKeyDown}
            />
          </div>
        </div>

        <div className='flex items-center gap-3'>
          {user && (user.role === 'admin' || user.role === 'seller') && (
            <Link
              to='/admin'
              className='text-on-surface-variant hover:text-primary text-sm font-medium transition-colors'
            >
              Admin
            </Link>
          )}

          {user ? (
            <div className='flex items-center gap-3'>
              <span className='text-sm text-on-surface-variant font-medium hidden md:block'>
                {user.email}
              </span>
              <button
                onClick={() => {
                  logout();
                  navigate("/");
                }}
                className='px-5 py-2 rounded-lg font-semibold border border-outline-variant/30 text-on-surface hover:bg-surface-container transition-colors active:scale-95'
              >
                Sign out
              </button>
            </div>
          ) : (
            <button
              onClick={() => navigate("/signin")}
              className='bg-primary text-on-primary px-5 py-2 rounded-lg font-semibold signature-gradient active:scale-95 transition-transform hover:opacity-90'
            >
              Sign In
            </button>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
