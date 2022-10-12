import { axiosClient } from '~/api';

export const searchService = {
    getResultSearchApi(value) {
        return axiosClient.get(`/products?q=${value}`);
    },
};
