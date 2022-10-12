import { axiosClient } from '~/api';

export const commentService = {
    getCommentByProductId(id) {
        return axiosClient.get(`/comments/productId/${id}`);
    },

    postComment(data) {
        return axiosClient.post(`/comments`, data);
    },
};
