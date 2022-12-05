import { axiosClient } from '~/api';

const branch_api = '/orders';

export const orderService = {
    getAllOrders({ page = null, limit = null, sortField = null, sortDir = null }) {
        const params = {
            page: page,
            limit: limit,
            sortField: sortField,
            sortDir: sortDir,
        };
        const query = Object.keys(params).reduce((acc, key) => {
            const value = params[key];
            return value ? acc + `${key}=${params[key]}&` : acc;
        }, '');
        return axiosClient.get(`${branch_api}?${query}`);
    },
    getAllOrdersByUserId(userId) {
        return axiosClient.get(`${branch_api}/userId/${userId}`);
    },
    postOrder(data) {
        return axiosClient.post(`${branch_api}`, data);
    },
    updateOrder(data, id) {
        return axiosClient.put(`${branch_api}/${id}`, data);
    },
    updatePaymentOrder(data, id) {
        return axiosClient.put(`${branch_api}/${id}/payment`, data);
    },
    updateStatus(id, data) {
        return axiosClient.put(`${branch_api}/${id}/status`, data);
    },
    deleteOrderById(id) {
        return axiosClient.delete(`${branch_api}/${id}`);
    },
};
