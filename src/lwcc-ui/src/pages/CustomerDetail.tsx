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
            api.get(`/v1/customers/${id}`)
            .then(response => setCustomer(response.data))
            .catch(() => {
                msgs.current.show({ severity: 'error', summary: 'Error', detail: 'Kundendaten konnten nicht geladen werden' });
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
                    await api.put('/v1/customers', customer).then(response => {
                        setCustomer(response.data);
                        msgs.current.show({ severity: 'success', summary: 'Aktualisiert', detail: 'Kundendaten wurden aktualisiert' });
                    })
                } else {
                    await api.post('/v1/customers', customer).then(response => {
                        setCustomer(response.data);
                        msgs.current.show({ severity: 'success', summary: 'Angelegt', detail: 'Kunde wurde angelegt' });
                    })
                }
            } catch (error) {
                if (error.response.data) {
                    msgs.current.show({ severity: 'error', summary: 'Error', detail: error.response.data.detail });
                } else {
                    msgs.current.show({ severity: 'error', summary: 'Error', detail: 'Fehler beim speichern der Kundendaten' });
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
            setFirstnameError('Vorname muss ausfüllt werden');
            isValid = false;
        } else {
            setFirstnameError('');
        }
        if (!customer.lastname) {
            setLastnameError('Nachname muss ausgefüllt werden');
            isValid = false;
        } else {
            setLastnameError('');
        }
        if (!isValidVatId(customer.vatId, customer.country)) {
            setVatIdError('Umsatzsteuer-ID ist ungültig');
            isValid = false;
        } else {
            setVatIdError('');
        }
        return isValid;
    };

    return (
        <div className="customer-form-container">
            <Messages ref={msgs} />
            <h2 className="form-title">{customer.id ? 'Kundendaten bearbeiten' : 'Kunde anlegen'}</h2>
            <form onSubmit={handleSubmit} className="customer-form-grid">
                <div className="flex flex-column gap-2">
                    <label htmlFor="firstname">Vorname</label>
                    <InputText id="firstname" name="firstname" value={customer.firstname} onChange={handleChange}
                               className={firstnameError && 'p-invalid'}/>
                    {firstnameError && <small className="p-error">{firstnameError}</small>}
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="lastname">Nachname</label>
                    <InputText id="lastname" name="lastname" value={customer.lastname} onChange={handleChange}
                               className={lastnameError && 'p-invalid'}/>
                    {lastnameError && <small className="p-error">{lastnameError}</small>}
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="infoText">Freitext Information</label>
                    <InputTextarea rows={5} cols={30} autoResize={true} id="infoText" name="infoText" value={customer.infoText} maxLength={100} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="vatId">Umsatzsteuer-ID</label>
                    <InputText id="vatId" name="vatId" value={customer.vatId} onChange={handleChange}
                               className={vatIdError && 'p-invalid'}/>
                    {vatIdError && <small className="p-error">{vatIdError}</small>}
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="street">Straße</label>
                    <InputText id="street" name="street" value={customer.street} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="houseNumber">Hausnummer</label>
                    <InputText id="houseNumber" name="houseNumber" value={customer.houseNumber} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="zip">PLZ</label>
                    <InputText id="zip" name="zip" value={customer.zip} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="city">Stadt</label>
                    <InputText id="city" name="city" value={customer.city} onChange={handleChange} />
                </div>

                <div className="flex flex-column gap-2">
                    <label htmlFor="country">Land</label>
                    <Dropdown id="country" value={customer.country} onChange={handleCountryChange} options={countryOptions} optionLabel="name"
                              placeholder="Select a Country" className="w-full md:w-14rem" />
                </div>

                <div className="form-footer">
                    <Button
                        label={customer.id ? 'Aktualisieren' : 'Anlegen'}
                        icon={customer.id ? 'pi pi-pencil' : 'pi pi-plus'}
                        type="submit"
                        loading={loading}
                        className="p-button-sm"
                    />

                    <Button
                        label='Abbrechen'
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
