import { createBrowserRouter } from 'react-router-dom';
import MainLayout from './layouts/MainLayout';
import AdminLayout from './layouts/AdminLayout';
import HomePage from './pages/HomePage';
import EventDetailPage from './pages/EventDetailPage';
import CreateEventPage from './pages/admin/CreateEventPage';
import TicketFetcherPage from './pages/admin/TicketFetcherPage';
import OrdersPage from './pages/admin/OrdersPage';
import SignIn from './pages/SignIn';
import RegisterPage from './pages/RegisterPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <MainLayout><HomePage /></MainLayout>,
  },
  {
    path: '/events/:id',
    element: <MainLayout><EventDetailPage /></MainLayout>,
  },
  {
    path: '/signin',
    element: <SignIn />,
  },
  {
    path: '/register',
    element: <RegisterPage />,
  },
  {
    path: '/admin/events/create',
    element: <AdminLayout><CreateEventPage /></AdminLayout>,
  },
  {
    path: '/admin/ticket-fetcher',
    element: <AdminLayout><TicketFetcherPage /></AdminLayout>,
  },
  {
    path: '/admin/orders',
    element: <AdminLayout><OrdersPage /></AdminLayout>,
  },
]);

export default router;