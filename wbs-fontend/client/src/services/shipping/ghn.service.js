import { toAddressSlug, toFullAddress } from '../../utils/utils';
import { GHN_CONFIG } from '../../utils/variableDefault';
import axiosGHN from './axios.config';

export const ghn = {
    async getPreviewOrderGHN (order) {
        let addressId = await this.getAddressGHN(order?.address);
        let items = order?.orderItems.map((item) => {
            return {
                name: item.product?.name,
                code: item.product?.slug,
                quantity: item.quantity,
                price: item.product?.salePrice,
                weight: item.product?.weight || GHN_CONFIG.weight,
                length: Number.parseInt(item.product?.packagingLength || GHN_CONFIG.length),
                width: Number.parseInt(item.product?.packagingWidth || GHN_CONFIG.width),
                height: Number.parseInt(Math.floor(item.product?.packagingHeight || GHN_CONFIG.height)),
                category: {
                    level1: 'Áo',
                },
            };
        });
        let config = items.reduce(
            (acc, item) => {
                return {
                    totalWeight: acc.totalWeight + item.weight * item.quantity,
                    totalLength: acc.totalLength + item.length * item.quantity,
                    totalWidth: acc.totalWidth + item.width * item.quantity,
                    totalHeight: acc.totalHeight + item.height * item.quantity,
                }
            },
            { totalWeight: 0, totalLength: 0, totalWidth: 0, totalHeight: 0 },
        );
        items = items.map(item =>{
            return {name: item.name, quantity: item.quantity}
        })
        let data = {
            payment_type_id: 2,
            note: order?.note,
            required_note: 'KHONGCHOXEMHANG',
            to_name: order?.fullName || GHN_CONFIG.toName,
            to_phone: order?.phone,
            to_address: toFullAddress(order?.address),
            to_ward_code: addressId?.wardCode || GHN_CONFIG.wardCode,
            to_district_id: addressId?.districtId || GHN_CONFIG.districtId,
            cod_amount: 0,
            content: 'ABCDEF',
            weight: (config.totalWeight < GHN_CONFIG.totalWeight ? config.totalWeight : GHN_CONFIG.totalWeight) || 1,
            length: (config.totalLength < GHN_CONFIG.totalLength ? config.totalLength : GHN_CONFIG.totalLength) || 1,
            width: (config.totalWidth < GHN_CONFIG.totalWidth ? config.totalWidth : GHN_CONFIG.totalWidth) || 1,
            height: (config.totalHeight < GHN_CONFIG.totalHeight ? config.totalHeight : GHN_CONFIG.totalHeight) || 1,
            pick_station_id: 0,
            insurance_value: order?.totalPrice < GHN_CONFIG.maxInsuranceValue ? order?.totalPrice : GHN_CONFIG.maxInsuranceValue,
            service_id: 0,
            service_type_id: 2,
            coupon: null,
            // pick_shift: [2],
            items: items,
        };
        console.log('data', data);
        return axiosGHN.post('https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/preview', data);
    },
    createOrderGHN(order) {
        let items = order?.orderItems.map((item) => {
            return {
                name: item.product?.name,
                code: item.product?.slug,
                quantity: item.quantity,
                price: item.product?.salePrice,
                weight: item.product?.weight || GHN_CONFIG.weight,
                length: Number.parseInt(item.product?.packagingLength || GHN_CONFIG.length),
                width: Number.parseInt(item.product?.packagingWidth || GHN_CONFIG.width),
                height: Number.parseInt(Math.floor(item.product?.packagingHeight || GHN_CONFIG.height)),
                category: {
                    level1: 'Áo',
                },
            };
        });
        let config = items.reduce(
            (acc, item) => {
                return {
                    totalWeight: acc.totalWeight + item.weight * item.quantity,
                    totalLength: acc.totalLength + item.length * item.quantity,
                    totalWidth: acc.totalWidth + item.width * item.quantity,
                    totalHeight: acc.totalHeight + item.height * item.quantity,
                }
            },
            { totalWeight: 0, totalLength: 0, totalWidth: 0, totalHeight: 0 },
        );
        items = items.map(item =>{
            return {name: item.name, quantity: item.quantity}
        })
        let data = {
            payment_type_id: 2,
            note: order?.note,
            required_note: 'KHONGCHOXEMHANG',
            to_name: order?.fullName || GHN_CONFIG.toName,
            to_phone: order?.phone,
            to_address: toFullAddress(order?.address),
            to_ward_code: '20107',
            to_district_id: 1442,
            cod_amount: 0,
            content: 'ABCDEF',
            weight: (config.totalWeight < GHN_CONFIG.totalWeight ? config.totalWeight : GHN_CONFIG.totalWeight) || 1,
            length: (config.totalLength < GHN_CONFIG.totalLength ? config.totalLength : GHN_CONFIG.totalLength) || 1,
            width: (config.totalWidth < GHN_CONFIG.totalWidth ? config.totalWidth : GHN_CONFIG.totalWidth) || 1,
            height: (config.totalHeight < GHN_CONFIG.totalHeight ? config.totalHeight : GHN_CONFIG.totalHeight) || 1,
            pick_station_id: 0,
            insurance_value: order?.totalPrice < GHN_CONFIG.maxInsuranceValue ? order?.totalPrice : GHN_CONFIG.maxInsuranceValue,
            service_id: 0,
            service_type_id: 2,
            coupon: null,
            // pick_shift: [2],
            items: items,
        };
        console.log('data', data);
        return axiosGHN.post('https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create', data);
    },
    cancelOrderGHN(orderCode) {
        return axiosGHN.post('https://online-gateway.ghn.vn/shiip/public-api/v2/switch-status/cancel', {order_codes: [orderCode]});
    },
    getOrderDetailGHN(orderCode) {
        return axiosGHN.post('https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/detail', {order_code: orderCode});
    },
    async getAddressGHN(address) {
        address = toAddressSlug(address);
        let addressId = {
            provinceId: GHN_CONFIG.provinceId, 
            districtId: GHN_CONFIG.districtId, 
            wardCode: GHN_CONFIG.wardCode,
        };

        let resProvince = await this.getProvinceList();
        let provinceList = resProvince.data?.data || [];
        for (let province of provinceList) {
            if (province?.ProvinceName.toLowerCase().includes(address?.city)) {
                addressId.provinceId = province?.ProvinceID;
                break;
            }
        }
        if (addressId.provinceId) {
            let resDistrict = await this.getDistrictList(addressId.provinceId);
            let districtList = resDistrict.data?.data || [];
            for (let district of districtList) {
                if (district?.DistrictName.toLowerCase().includes(address?.district)) {
                    addressId.districtId = district?.DistrictID;
                    break;
                }
            }
            if (addressId?.districtId) {
                let resWard = await this.getWardList(addressId.districtId);
                let wardList = resWard.data?.data || [];
                for (let ward of wardList) {
                    if (ward?.WardName.toLowerCase().includes(address?.ward)) {
                        addressId.wardCode = ward?.WardCode;
                        break;
                    }
                }
                return addressId;
            }
        }
        return {
            provinceId: GHN_CONFIG.provinceId, 
            districtId: GHN_CONFIG.districtId, 
            wardCode: GHN_CONFIG.wardCode,
        };
    },
    getProvinceList() {
        return axiosGHN.get('https://online-gateway.ghn.vn/shiip/public-api/master-data/province');
    },
    getDistrictList(provinceId) {
        return axiosGHN.get(
            `https://online-gateway.ghn.vn/shiip/public-api/master-data/district?province_id=${provinceId}`,
        );
    },
    getWardList(districtId) {
        return axiosGHN.get(
            `https://online-gateway.ghn.vn/shiip/public-api/master-data/ward?district_id=${districtId}`,
        );
    },
};
