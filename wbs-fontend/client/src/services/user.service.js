import { axiosClient } from '../api';

export const userService = {
    getAllUsers() {
        return axiosClient.get(`/users`);
    },
    getUserById(id) {
        return axiosClient.get(`/users/${id}`);
    },
    getUserByToken() {
        return axiosClient.get(`/users/access-token`);
    },
    editUser(phone, data) {
        return axiosClient.patch(`/users/${phone}`, data);
    }
};
