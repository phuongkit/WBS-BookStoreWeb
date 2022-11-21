import { toFullAddress } from '../../utils/utils';
import axiosGHN from './axios.config';

export default ghn = {
    createOrder(order) {
        data = {
            payment_type_id: 2,
            note: order?.note,
            required_note: 'KHONGCHOXEMHANG',
            to_name: order?.fullName,
            to_phone: order?.phone,
            to_address: toFullAddress(order?.address),
            to_ward_code: '20107',
            to_district_id: 1442,
            cod_amount: 0,
            content: 'ABCDEF',
            weight: 200,
            length: 15,
            width: 15,
            height: 15,
            pick_station_id: 0,
            insurance_value: order?.totalPrice,
            service_id: 0,
            service_type_id: 2,
            coupon: null,
            pick_shift: [2],
            items: [
                {
                    name: 'Áo Polo',
                    code: 'Polo123',
                    quantity: 1,
                    price: 200000,
                    length: 12,
                    width: 12,
                    height: 12,
                    category: {
                        level1: 'Áo',
                    },
                },
            ],
        };
        return axiosGHN.post('https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create', data);
    },
};
