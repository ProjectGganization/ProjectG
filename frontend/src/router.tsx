import { createBrowserRouter } from 'react-router-dom';
import MainLayout from './layouts/MainLayout';
import AdminLayout from './layouts/AdminLayout';
import HomePage from './pages/HomePage';
import EventDetailPage from './pages/EventDetailPage';
import CreateEventPage from './pages/admin/CreateEventPage';
import TicketFetcherPage from './pages/admin/TicketFetcherPage';

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
    path: '/admin/events/create',
    element: <AdminLayout><CreateEventPage /></AdminLayout>,
  },
  {
    path: '/admin/ticket-fetcher',
    element: <AdminLayout><TicketFetcherPage /></AdminLayout>,
  },
]);

export default router;
