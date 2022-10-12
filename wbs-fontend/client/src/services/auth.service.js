import { axiosClient } from '~/api/';

export const authService = {

    postLogin(data) {
        return axiosClient.post(`/auth/login`,data);
    },
    postRegister(data) {
        return axiosClient.post(`/auth/register`,data);
    },
    
};
