import { useParams } from 'react-router-dom';

function CustomerDetail() {

    const { id } = useParams();
    return (
        <div>
            <h1>Customer Detail {id}</h1>
        </div>
    );
}

export default CustomerDetail;
