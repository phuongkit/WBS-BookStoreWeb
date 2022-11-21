import { axiosClient } from './../../api';

const branch_api = "/payment";

export const vnpay = {

    createVNPayPayment(data) {
        return axiosClient.post(`${branch_api}/vnpay/create-payment-url`,data);
    },
    getReturnVNPay() {
        return axiosClient.post(`${branch_api}/vnpay/return`);
    }
};