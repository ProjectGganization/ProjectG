import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import apiClient from '../api/apiClient';

const RegisterPage = () => {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    firstname: '',
    lastname: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: '',
  });
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm((f) => ({ ...f, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (form.password !== form.confirmPassword) {
      setError('Salasanat eivät täsmää.');
      return;
    }

    setLoading(true);
    try {
      await apiClient.post('/api/register', {
        firstname: form.firstname,
        lastname: form.lastname,
        email: form.email,
        phone: form.phone,
        password: form.password,
      });
      navigate('/signin');
    } catch (err) {
      const status = (err as Error & { status?: number }).status;
      if (status === 409) {
        setError('Sähköposti on jo käytössä.');
      } else if (status === 400) {
        setError('Tarkista syötteet. Salasanan tulee olla vähintään 8 merkkiä pitkä.');
      } else {
        setError('Rekisteröityminen epäonnistui. Yritä uudelleen.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-background px-4 py-12">
      {/* Background decoration */}
      <div className="absolute inset-0 overflow-hidden pointer-events-none">
        <div className="absolute -top-40 -right-40 w-96 h-96 bg-primary/5 rounded-full blur-3xl" />
        <div className="absolute -bottom-40 -left-40 w-96 h-96 bg-primary/5 rounded-full blur-3xl" />
      </div>

      <div className="relative w-full max-w-md">
        <div className="bg-surface-container-lowest border border-outline-variant/20 rounded-2xl p-8 shadow-ambient-lg">

          {/* Brand */}
          <div className="mb-8 text-center">
            <Link to="/" className="inline-block">
              <span className="text-3xl font-black tracking-tighter text-on-surface">TicketGG</span>
            </Link>
            <p className="mt-2 text-on-surface-variant text-sm">Luo uusi tili</p>
          </div>

          {/* Error */}
          {error && (
            <div className="mb-5 flex items-center gap-2 bg-error/10 border border-error/20 text-error text-sm px-4 py-3 rounded-lg">
              <span className="material-symbols-outlined text-base">error</span>
              {error}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            {/* Name row */}
            <div className="grid grid-cols-2 gap-3">
              <div>
                <label className="block text-sm font-medium text-on-surface mb-1.5">Etunimi</label>
                <input
                  required
                  name="firstname"
                  type="text"
                  value={form.firstname}
                  onChange={handleChange}
                  placeholder="Matti"
                  className="w-full bg-surface-container border border-outline-variant/30 rounded-lg px-4 py-2.5 text-on-surface placeholder-outline focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-on-surface mb-1.5">Sukunimi</label>
                <input
                  required
                  name="lastname"
                  type="text"
                  value={form.lastname}
                  onChange={handleChange}
                  placeholder="Meikäläinen"
                  className="w-full bg-surface-container border border-outline-variant/30 rounded-lg px-4 py-2.5 text-on-surface placeholder-outline focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
              </div>
            </div>

            {/* Email */}
            <div>
              <label className="block text-sm font-medium text-on-surface mb-1.5">Sähköposti</label>
              <div className="relative">
                <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-xl">mail</span>
                <input
                  required
                  name="email"
                  type="email"
                  value={form.email}
                  onChange={handleChange}
                  placeholder="matti@esimerkki.fi"
                  className="w-full bg-surface-container border border-outline-variant/30 rounded-lg pl-10 pr-4 py-2.5 text-on-surface placeholder-outline focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
              </div>
            </div>

            {/* Phone */}
            <div>
              <label className="block text-sm font-medium text-on-surface mb-1.5">Puhelinnumero</label>
              <div className="relative">
                <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-xl">phone</span>
                <input
                  required
                  name="phone"
                  type="tel"
                  value={form.phone}
                  onChange={handleChange}
                  placeholder="0401234567"
                  className="w-full bg-surface-container border border-outline-variant/30 rounded-lg pl-10 pr-4 py-2.5 text-on-surface placeholder-outline focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
              </div>
            </div>

            {/* Password */}
            <div>
              <label className="block text-sm font-medium text-on-surface mb-1.5">Salasana</label>
              <div className="relative">
                <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-xl">lock</span>
                <input
                  required
                  name="password"
                  type={showPassword ? 'text' : 'password'}
                  value={form.password}
                  onChange={handleChange}
                  placeholder="••••••••"
                  className="w-full bg-surface-container border border-outline-variant/30 rounded-lg pl-10 pr-10 py-2.5 text-on-surface placeholder-outline focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-on-surface-variant hover:text-on-surface transition-colors"
                >
                  <span className="material-symbols-outlined text-xl">
                    {showPassword ? 'visibility_off' : 'visibility'}
                  </span>
                </button>
              </div>
            </div>

            {/* Confirm password */}
            <div>
              <label className="block text-sm font-medium text-on-surface mb-1.5">Vahvista salasana</label>
              <div className="relative">
                <span className="material-symbols-outlined absolute left-3 top-1/2 -translate-y-1/2 text-on-surface-variant text-xl">lock</span>
                <input
                  required
                  name="confirmPassword"
                  type={showPassword ? 'text' : 'password'}
                  value={form.confirmPassword}
                  onChange={handleChange}
                  placeholder="••••••••"
                  className="w-full bg-surface-container border border-outline-variant/30 rounded-lg pl-10 pr-4 py-2.5 text-on-surface placeholder-outline focus:outline-none focus:ring-2 focus:ring-primary/20 focus:border-primary transition-all"
                />
              </div>
            </div>

            {/* Submit */}
            <button
              type="submit"
              disabled={loading}
              className="w-full signature-gradient text-on-primary font-semibold py-2.5 rounded-lg transition-all duration-200 active:scale-95 hover:opacity-90 disabled:opacity-60 disabled:cursor-not-allowed flex items-center justify-center gap-2 mt-2"
            >
              {loading ? (
                <>
                  <svg className="animate-spin h-4 w-4" viewBox="0 0 24 24" fill="none">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z" />
                  </svg>
                  Rekisteröidytään...
                </>
              ) : (
                'Luo tili'
              )}
            </button>
          </form>

          <div className="my-6 flex items-center gap-3">
            <div className="flex-1 h-px bg-outline-variant/30" />
            <span className="text-on-surface-variant text-xs">tai</span>
            <div className="flex-1 h-px bg-outline-variant/30" />
          </div>

          <p className="text-center text-sm text-on-surface-variant">
            Onko sinulla jo tili?{' '}
            <Link to="/signin" className="text-primary hover:opacity-75 font-medium transition-opacity">
              Kirjaudu sisään
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;
