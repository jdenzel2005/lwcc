import { Menubar } from 'primereact/menubar';
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
    const navigate = useNavigate();

    const items = [
        { label: 'Home', icon: 'pi pi-home', command: () => navigate('/') },
        { label: 'Create', icon: 'pi pi-plus', command: () => navigate('/customers/1') },
    ];

    return <Menubar model={items} />;
};

export default Navbar;
