import 'primereact/resources/themes/lara-light-indigo/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
import './App.css';
import Layout from './layouts/Layout.tsx';
import AppRoutes from './routes/AppRoutes.tsx';

function App() {

    return (
        <Layout>
            <AppRoutes/>
        </Layout>
    );
}

export default App;
