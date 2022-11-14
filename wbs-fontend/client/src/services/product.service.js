import { axiosClient } from '~/api';

const branch_api = '/products';

export const productService = {
    getProducts(page, limit) {
        // return axiosClient.get(`${branch_api}?_page=${page}&_limit=${limit}`);
        return axiosClient.get(`${branch_api}?page=${page}&limit=${limit}`);
    },
    getAllProducts() {
        return axiosClient.get(`${branch_api}`);
    },
    getAllProductsByOption(homeOptionId) {
        return axiosClient.get(`${branch_api}/option/${homeOptionId}`);
    },
    getProduct(id) {
        return axiosClient.get(`${branch_api}/${id}`);
    },
    getProductByName(name) {
        return axiosClient.get(`${branch_api}/${name}`);
    },
    getProductByCategory(category) {
        return axiosClient.get(`${branch_api}?category=${category}`);
    },
    getProductByCategoryId(categoryId, { page = null, limit = null, location = null }) {
        const params = {
            'page': page,
            'limit': limit,
            'location': location,
        };
        const query = Object.keys(params).reduce((acc, key) => {
            const value = params[key];
            return value ? acc + `${key}=${params[key]}&` : acc;
        }, '');
        return axiosClient.get(`${branch_api}/categoryId/${categoryId}?${query}`);
    },
    getProductByBrandId(brandId) {
        return axiosClient.get(`${branch_api}/brandId/${brandId}`);
    },
    getProductByCategoryIdAndBrandId(categoryId, brandId) {
        return axiosClient.get(`${branch_api}/categoryId/${categoryId}/brandId/${brandId}`);
    },
    getProductByPolicy(category, brand) {
        return axiosClient.get(`${branch_api}?category=${category}${brand}`);
    },
    getProductBySlug(slug) {
        return axiosClient.get(`${branch_api}/slug/${slug}`);
    },
    getProductByLocation(location) {
        return axiosClient.get(`${branch_api}?location=${location}`);
    },
    queryProduct() {
        const query = Array.from(arguments)
            .map((param) => {
                return `${param[0]}=${param[1]}`;
            })
            .join('&');
        return axiosClient.get(`${branch_api}?${query}`);
    },
    queryProductWatch(string) {
        const query = string
            .map((e) => {
                const key = Object.keys(e);
                const value = Object.values(e);
                return `${key[0]}=${value[0]}`;
            })
            .join('&');
        return axiosClient.get(`${branch_api}?category=watch&${query}`);
    },
    // queryProduct(["brand", "nokia"], ["title", "Nokia 500"])
    getArticle(id) {
        return axiosClient.get(`/product/article?productId=${id}`);
    },
};
