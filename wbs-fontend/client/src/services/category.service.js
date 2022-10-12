import { axiosClient } from '~/api';

const branch_api = "/categories";

export const categoryService = {
    getAllCategories() {
        return axiosClient.get(`${branch_api}`);
    },
    getCategoryBySlug(slug) {
        return axiosClient.get(`${branch_api}/slug/${slug}`);
    },
};
