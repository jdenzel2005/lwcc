import 'primereact/resources/themes/lara-light-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import './Layout.css';
import Navbar from '../components/Navbar.tsx';

const Layout = ({ children }) => {
    return (
        <div className="layout-container">
            <header className="layout-header">
                <Navbar/>
            </header>

            <div className="layout-content">
                {children}
            </div>

            <footer className="layout-footer">
                © 2025 Lexware Coding Challenge
            </footer>
        </div>
    );
};

export default Layout;
