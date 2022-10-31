import { axiosClient } from '~/api';

const branch_api = "/categories";

export const categoryService = {
    getAllCategories() {
        return axiosClient.get(`${branch_api}`);
    },
    getAllCategoriesWithHierarchy() {
        return axiosClient.get(`${branch_api}/hierarchy`);
    },
    getCategoryBySlug(slug) {
        return axiosClient.get(`${branch_api}/slug/${slug}`);
    },
};
