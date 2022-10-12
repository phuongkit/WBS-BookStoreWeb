import { axiosClient } from '~/api';

const branch_api = "/feedbacks";

export const ratingService = {
    getRating(id) {
        return axiosClient.get(`${branch_api}/productId/${id}`);
    },
    postRating(data) {
        return axiosClient.post(`${branch_api}`, data);
    },
    patchRating(id, data) {
        return axiosClient.patch(`${branch_api}/${id}`, data);
    },
};
