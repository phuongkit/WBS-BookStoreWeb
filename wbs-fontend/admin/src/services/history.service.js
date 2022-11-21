import { axiosClient } from '~/api';

export const historyService = {
    getHistoryOrderByPhone(value) {
        return axiosClient.get(`/orders?customer.phone=${value}`);
    },
    updateHistoryOrder(id, data) {
        return axiosClient.patch(`/orders/${id}`, data);
    },
};
