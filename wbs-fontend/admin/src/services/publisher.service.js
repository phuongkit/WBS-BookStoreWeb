import { axiosClient } from '~/api/';

const branch_api = "/publishers";

export const publisherService = {

    getAll() {
        return axiosClient.get(`${branch_api}`);
    },
    getById(id) {
        return axiosClient.get(`${branch_api}/${id}`);
    },
    create(data) {
        return axiosClient.post(`${branch_api}`, data);
    },
    create(id, data) {
        return axiosClient.put(`${branch_api}/${id}`, data);
    },
    deleteById(id) {
        return axiosClient.delete(`${branch_api}/${id}`);
    }
};