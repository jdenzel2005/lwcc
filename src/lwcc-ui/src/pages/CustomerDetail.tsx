import { useEffect, useRef, useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Messages } from 'primereact/messages';
import type { Customer } from '../types/customer';
import { useNavigate, useParams } from 'react-router-dom';
import './CustomerDetail.css';
import api from '../api/axios.ts';
import { Dropdown } from 'primereact/dropdown';
import { InputTextarea } from 'primereact/inputtextarea';
import { isValidVatId } from '../validation/VatIdValidator.ts';

function CustomerDetail() {
    const navigate = useNavigate();
    const { id } = useParams();
    const [customer, setCustomer] = useState({
        id: '',
        firstname: '',
        lastname: '',
        infoText: '',
        vatId: '',
        street: '',
        houseNumber: '',
        zip: '',
        city: '',
        country: 'DE'

    } as Customer);
    const [loading, setLoading] = useState(false);
    const [firstnameError, setFirstnameError] = useState('');
    const [lastnameError, setLastnameError] = useState('');
    const [vatIdError, setVatIdError] = useState('');

    const msgs = useRef(null);
    const isExistingCustomer = Boolean(id);

    const countryOptions = ['DE','AT','FR','GB','DK','NL'];

    useEffect(() => {
        if (isExistingCustomer) {
            // Fetch customer details
            api.get(`/customers/${id}`)
            .then(response => setCustomer(response.data))
            .catch(() => {
                msgs.current.show({ severity: 'error', summary: 'Error', detail: 'Failed to load customer data' });
            });
        }
    }, [id, isExistingCustomer]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setCustomer(prev => ({ ...prev, [name]: value }));
    };

    const handleCountryChange = (e: any) => {
        setCustomer(prev => ({
            ...prev,
            country: e.value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (validateForm()) {
            setLoading(true);
            try {
                if (customer.id) {
                    await api.put('/customers', customer).then(response => {
                        setCustomer(response.data);
                        msgs.current.show({ severity: 'success', summary: 'Updated', detail: 'Customer updated.' });
                    })
                } else {
                    await api.post('/customers', customer).then(response => {
                        setCustomer(response.data);
                        msgs.current.show({ severity: 'success', summary: 'Created', detail: 'Customer created.' });
                    })
                }
            } catch (error) {
                if (error.response.data) {
                    msgs.current.show({ severity: 'error', summary: 'Error', detail: error.response.data.detail });
                } else {
                    msgs.current.show({ severity: 'error', summary: 'Error', detail: 'Error saving customer' });
                }
            } finally {
                setLoading(false);
            }
        }
    };

    const handleCancel = () => {
        navigate('/');
    }

    const validateForm = () => {
        let isValid = true;
        if (!customer.firstname) {
            setFirstnameError('First name is required');
            isValid = false;
        } else {
            setFirstnameError('');
        }
        if (!customer.lastname) {
            setLastnameError('Last name is required');
            isValid = false;
        } else {
            setLastnameError('');
        }
        if (!isValidVatId(customer.vatId, customer.country)) {
            setVatIdError('VAT ID is invalid');
            isValid = false;
        } else {
            setVatIdError('');
        }
        return isValid;
    };

    return (
        <div className="customer-form-container">
            <Messages ref={msgs} />
            <h2 className="form-title">{customer.id ? 'Edit Customer' : 'Create Customer'}</h2>
            <form onSubmit={handleSubmit} className="customer-form-grid">
                <div className="flex flex-column gap-2">
                    <label htmlFor="firstname">First name</label>
                    <InputText id="firstname" name="firstname" value={customer.firstname} onChange={handleChange}
                               className={firstnameError && 'p-invalid'}/>
                    {firstnameError && <small className="p-error">{firstnameError}</small>}
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="lastname">Last name</label>
                    <InputText id="lastname" name="lastname" value={customer.lastname} onChange={handleChange}
                               className={lastnameError && 'p-invalid'}/>
                    {lastnameError && <small className="p-error">{lastnameError}</small>}
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="infoText">Info text</label>
                    <InputTextarea rows={5} cols={30} autoResize={true} id="infoText" name="infoText" value={customer.infoText} maxLength={100} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="vatId">VAT ID</label>
                    <InputText id="vatId" name="vatId" value={customer.vatId} onChange={handleChange}
                               className={vatIdError && 'p-invalid'}/>
                    {vatIdError && <small className="p-error">{vatIdError}</small>}
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="street">Street</label>
                    <InputText id="street" name="street" value={customer.street} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="houseNumber">House number</label>
                    <InputText id="houseNumber" name="houseNumber" value={customer.houseNumber} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="zip">ZIP</label>
                    <InputText id="zip" name="zip" value={customer.zip} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="city">City</label>
                    <InputText id="city" name="city" value={customer.city} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="country">Country</label>
                    <Dropdown id="country" value={customer.country} onChange={handleCountryChange} options={countryOptions} optionLabel="name"
                              placeholder="Select a Country" className="w-full md:w-14rem" />
                </div>

                <div className="form-footer">
                    <Button
                        label={customer.id ? 'Update' : 'Create'}
                        icon={customer.id ? 'pi pi-pencil' : 'pi pi-plus'}
                        type="submit"
                        loading={loading}
                        className="p-button-sm"
                    />

                    <Button
                        label='Cancel'
                        icon='pi pi-times'
                        type="submit"
                        className="p-button-sm"
                        severity="danger"
                        onClick={handleCancel}
                    />
                </div>
            </form>
        </div>
    );
}

export default CustomerDetail;
