import { axiosClient } from '~/api';

const branch_api = "/orders";

export const orderService = {
    getAllOrders() {
        return axiosClient.get(`${branch_api}`);
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
