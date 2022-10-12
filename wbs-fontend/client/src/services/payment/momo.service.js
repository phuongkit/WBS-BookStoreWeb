import axios from 'axios';
export const momo = {
    createMomoPayment(data) {
        return axios.post('https://momopayment.glitch.me/paymentUrl', data);
    },
};
