import axios from 'axios';

const apiProduction = 'http://localhost:8080/api/v1';
const apiDev = 'http://localhost:8080/api/v1';

const baseURL = process.env.NODE_ENV === 'production' ? apiProduction : apiDev;

const axiosClient = axios.create({
    baseURL,
    withCredentials: false,
    headers: {
        'Content-Type': 'application/json',
    },
});

axiosClient.interceptors.request.use(
    function (req) {
        // const token = JSON.parse(localStorage.getItem('token'));
        const token = 'Bearer ' + JSON.parse(localStorage.getItem('access'));
        // if (token) req.headers['auth-token'] = token;
        if (token) req.headers['Authorization'] = token;
        return req;
    },

    function (error) {
        return Promise.reject(error);
    },
);
axiosClient.interceptors.response.use(
    function (res) {
        return res.data;
    },

    function (error) {
        return Promise.reject(error);
    },
);
export default axiosClient;
