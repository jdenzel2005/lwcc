import { Route, Routes } from 'react-router-dom';
import CustomerList from '../pages/CustomerList.tsx';
import CustomerDetail from '../pages/CustomerDetail.tsx';

const AppRoutes = () => (
    <Routes>
        <Route path="/" element={<CustomerList />} />
        <Route path="/customers/:id" element={<CustomerDetail />} />
    </Routes>
);

export default AppRoutes;
