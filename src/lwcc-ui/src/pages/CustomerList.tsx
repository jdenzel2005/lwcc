import { useEffect, useState } from 'react';

import 'primereact/resources/themes/lara-light-indigo/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Paginator } from 'primereact/paginator';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios.ts';

function CustomerList() {
    const navigate = useNavigate();
    const [first, setFirst] = useState(0);
    const [page, setPage] = useState(0);
    const [rows, setRows] = useState(10);
    const [sort, setSort] = useState('id');
    const [direction, setDirection] = useState('ASC');
    const [data, setData] = useState(null);       // to store fetched data
    const [loading, setLoading] = useState(true); // to manage loading state
    const [error, setError] = useState(null);     // to handle errors

    const onPageChange = (event) => {
        setFirst(event.first);
        setPage(event.page);
        setRows(event.rows);
    };

    const onSort = (event) => {
        setSort(event.sortField);
        setDirection(direction === 'ASC' ? 'DESC' : 'ASC');
    }

    const onRowClick = (e: { data: any }) => {
        navigate(`/customers/${e.data.id}`);
    };

    useEffect(() => {
        api.get(`/v1/customers/list?first=${page}&rows=${rows}&sort=${sort}&direction=${direction}`)
        .then(response => {
            setData(response.data);     // success
            setLoading(false);
        })
        .catch(error => {
            setError(error);            // error
            setLoading(false);
        });
    }, [first, page, rows, sort, direction]); // empty dependency array = runs once on mount

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error occurred: {error.message}</p>;

    return (
        <div className="App">
            <div>
                <DataTable value={data.content} onSort={onSort}
                           header="Kunden"
                           selectionMode="single" onRowClick={onRowClick}
                           stripedRows showGridlines tableStyle={{ minWidth: '50rem' }}>
                    <Column field="firstname" sortable header="Vorname"></Column>
                    <Column field="lastname" sortable header="Nachname"></Column>
                    <Column field="vatId" sortable header="Umsatzsteuer-ID"></Column>
                    <Column field="city" sortable header="Stadt"></Column>
                    <Column field="country" sortable header="Land"></Column>
                </DataTable>
                <Paginator first={first} rows={rows} totalRecords={data.total} rowsPerPageOptions={[10, 20, 30]} onPageChange={onPageChange} />
            </div>
        </div>
    );
}

export default CustomerList;
