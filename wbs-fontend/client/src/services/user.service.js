import { axiosClient } from '~/api/';

export const userService = {
    getUserById(id) {
        return axiosClient.get(`/users?id=${id}`);
    },

    editUser(phone, data) {
        return axiosClient.patch(`/users/${phone}`, data);
    }
};
