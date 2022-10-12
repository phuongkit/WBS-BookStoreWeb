import { axiosClient } from '~/api';

export const orderService = {
    postOrder(data) {
        return axiosClient.post(`/orders/`, data);
    },
};
