import axios from 'axios';

const api = axios.create({
    baseURL: `/api`,
    headers: {
        'Content-Type': 'application/json',
    },
});

axios.defaults.withCredentials = true;

export default api;
