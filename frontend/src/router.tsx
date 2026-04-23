import { createBrowserRouter } from 'react-router-dom';
import MainLayout from './layouts/MainLayout';
import AdminLayout from './layouts/AdminLayout';
import HomePage from './pages/HomePage';
import CreateEventPage from './pages/admin/CreateEventPage';
import TicketFetcherPage from './pages/admin/TicketFetcherPage';
import SignIn from './pages/SignIn';

const router = createBrowserRouter([
  {
    path: '/',
    element: <MainLayout><HomePage /></MainLayout>,
  },
  {
    path: '/signin',
    element: <SignIn />,
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