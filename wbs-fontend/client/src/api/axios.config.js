import axios from 'axios';
// import { useNavigate } from 'react-router-dom';
import { handleLogout, postLogout } from '../redux/user/userApi';
import swal from 'sweetalert';

const apiProduction = process.env.BACKEND_URL + '/api/v1';
const apiDev = process.env.BACKEND_URL+ '/api/v1';

const baseURL = process.env.NODE_ENV === 'production' ? apiProduction : apiDev;

const axiosClient = axios.create({
    baseURL,
    withCredentials: false,
    headers: {
        'Content-Type': 'application/json',
    },
});

const createError = (httpStatusCode, statusCode, errorMessage, problems, errorCode = '') => {
    const error = new Error();
    error.httpStatusCode = httpStatusCode;
    error.statusCode = statusCode;
    error.errorMessage = errorMessage;
    error.problems = problems;
    error.errorCode = errorCode + '';
    return error;
};

// const navigate = useNavigate();

axiosClient.interceptors.request.use(
    function (req) {
        const token = localStorage.getItem('token');
        if (token && typeof token === 'string') {
            const token = 'Bearer ' + JSON.parse(localStorage.getItem('token'));
            // if (token) req.headers['auth-token'] = token;
            if (token) req.headers['Authorization'] = token;
        }
        return req;
    },

    function (error) {
        swal({
            title: 'Thông báo',
            text: 'Vui lòng đăng nhập lại',
            icon: 'warning',
        });
        postLogout();
        return Promise.reject(error);
    },
);
axiosClient.interceptors.response.use(
    function (res) {
        return res.data;
    },

    function (error) {
        const { response } = error;
        console.log(error);
        // const { data } = error.response;
        if (response?.status === 401) {
            swal({
                title: 'Thông báo',
                text: 'Vui lòng đăng nhập để thực hiện chức năng này',
                icon: 'warning',
            });
            postLogout();
        }
        // if (error.hasOwnProperty('code') && data.hasOwnProperty('message')) {
        //     return Promise.reject(createError(response.status, data['status'], data['message'], "error occurred"));
        // }
        return Promise.reject(error?.response?.data);
    },
);
export default axiosClient;
