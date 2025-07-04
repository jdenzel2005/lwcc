import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8002/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

axios.defaults.withCredentials = true;

export default api;
