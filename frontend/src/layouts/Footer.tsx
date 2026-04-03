const Footer = () => {
  return (
    <footer className="bg-slate-50 border-t border-slate-200 w-full text-sm font-inter text-slate-500">
      <div className="flex flex-col md:flex-row justify-between items-center px-8 py-12 space-y-4 md:space-y-0 max-w-screen-2xl mx-auto">
        <div className="flex flex-col gap-2">
          <span className="font-bold text-slate-900 text-lg">Precision Concierge</span>
          <p>© 2024 Precision Concierge. All rights reserved.</p>
        </div>
        <div className="flex gap-8 items-center">
          <a
            className="hover:text-slate-900 transition-colors hover:underline decoration-blue-500/30 underline-offset-4"
            href="#"
          >
            Contact Us
          </a>
          <a
            className="text-blue-600 font-medium hover:underline decoration-blue-500/30 underline-offset-4"
            href="#"
          >
            Home
          </a>
          <a
            className="hover:text-slate-900 transition-colors hover:underline decoration-blue-500/30 underline-offset-4 flex items-center gap-1"
            href="#"
          >
            Back to Top
            <span className="material-symbols-outlined text-xs">arrow_upward</span>
          </a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
