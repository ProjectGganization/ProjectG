import { createBrowserRouter } from 'react-router-dom';
import MainLayout from './layouts/MainLayout';
import AdminLayout from './layouts/AdminLayout';
import HomePage from './pages/HomePage';
import EventDetailPage from './pages/EventDetailPage';
import DashboardPage from './pages/admin/DashboardPage';
import CreateEventPage from './pages/admin/CreateEventPage';
import TicketFetcherPage from './pages/admin/TicketFetcherPage';
import OrdersPage from './pages/admin/OrdersPage';
import SignIn from './pages/SignIn';
import RegisterPage from './pages/RegisterPage';
import EditEventPage from './pages/admin/EditEventPage';
import SalesReportsPage from './pages/admin/SalesReports';
import VenuesPage from './pages/admin/VenuesPage';

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
    path: '/admin',
    element: <AdminLayout><DashboardPage /></AdminLayout>,
  },
  {
    path: '/admin/events/create',
    element: <AdminLayout><CreateEventPage /></AdminLayout>,
  },
  {
    path: '/admin/events/:id/edit',
    element: <AdminLayout><EditEventPage /></AdminLayout>,
  },
  {
    path: '/admin/ticket-fetcher',
    element: <AdminLayout><TicketFetcherPage /></AdminLayout>,
  },
  {
    path: '/admin/orders',
    element: <AdminLayout><OrdersPage /></AdminLayout>,
  },
  {
    path: '/admin/reports',
    element: <AdminLayout><SalesReportsPage /></AdminLayout>,
  },
  {
    path: '/admin/venues',
    element: <AdminLayout><VenuesPage /></AdminLayout>,
  }
]);

export default router;