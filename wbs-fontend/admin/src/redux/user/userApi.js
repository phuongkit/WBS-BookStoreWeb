import { Navigate } from 'react-router-dom';
import { authService, userService } from '../../services';
import { getPageUser, login, logout } from './userSlice';
import swal from 'sweetalert';

export const getAllUsersApi = async (dispatch) => {
    let res = await userService.getAllUsers();
    dispatch(getPageUser(res.data));
};

export const postLogin = async (dispatch, data, navigate) => {
    try {
        let res = await authService.postLogin(data);
        if (res.status === 'OK') {
            localStorage.setItem('token', JSON.stringify(res.data?.accessToken));
            delete res?.data?.tokenToken;
            localStorage.setItem('user', JSON.stringify(res.data));
            if (res.data.role === 0) {
                navigate('/');
            } else {
                navigate('/login');
            }
            dispatch(login(res.data));
        } else {
            swal({
                title: 'Đăng nhập thất bại',
                text: 'Sai tên đăng nhập hoặc mật khẩu',
                icon: 'error',
            });
        }
    } catch (error) {
        console.error(error);
    }
};
export const postRegister = async (dispatch, data, navigate) => {
    try {
        let res = await authService.postRegister(data);
        if (res.status === 'OK') {
            // localStorage.setItem('token', JSON.stringify(res.data.accessToken));
            // if (res.data.role === 0) {
            //     navigate('/Admin');
            // } else {
            //     navigate('/');
            // }
            // dispatch(login(res.data));
            swal({
                title: 'Đăng ký thành công',
                text: 'Vui lòng đăng nhập',
                icon: 'success',
            });
        } else {
            swal({
                title: 'Đăng ký không thành công',
                text: 'Vui lòng thử lại',
                icon: 'error',
            });
        }
    } catch (error) {
        console.error(error);
    }
};

export const getUserByToken = async (dispatch) => {
    try {
        let res = await userService.getUserByToken();
        if (res.status === 'OK') {
            dispatch(login(res.data));
        } else {
            dispatch(login(null));
            postLogout();
        }
    } catch (error) {
        console.error(error);
    }
};
export const logUserByToken = async (dispatch) => {
    try {
        dispatch(logout());
        localStorage.removeItem('token');
    } catch (error) {
        console.error(error);
    }
};

export const postLogout = (dispatch = null, navigate = null) => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    if (dispatch !== null) {
        dispatch(logout());
    }
    if (navigate !== null) {
        navigate('/');
    }
};
