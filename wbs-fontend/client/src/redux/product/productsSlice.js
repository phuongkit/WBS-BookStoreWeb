import { createSlice } from '@reduxjs/toolkit';

export const products = createSlice({
    name: 'products',
    initialState: {
        allProduct: {
            data: [],
        },
        pageProduct: {
            data: [],
        },
        oneProduct: {
            data: '',
        },
        filter: {
            data: [],
        },
        location: {
            data: [],
        },
        productDetail: {
            data: {},
        },
    },
    reducers: {
        getAllProducts: (state, action) => {
            state.allProduct.data = action.payload;
        },
        getPageProduct: (state, action, list) => {
            state.pageProduct.data = action.payload;
        },
        updateAllProduct: (state, action) => {
            state.allProduct.data = action.payload;
        },
        getOneProduct: (state, action) => {
            state.oneProduct.data = action.payload;
        },
        handleFilter: (state, action) => {
            state.filter.data = action.payload;
        },
        getLocationProduct: (state, action) => {
            state.location.data = action.payload;
        },
        getProductDetail: (state, action) => {
            state.productDetail.data = action.payload;
        },
        updateDiscussRating: (state, action) => {
            const rating = state.productDetail.data.rating.find((rating) => rating.id === action.payload.idRating);
            if (rating) {
                const res = rating.discuss.push(action.payload);
            }
        },
    },
});
export const {
    updateDiscussRating,
    getAllProducts,
    getPageProduct,
    getOneProduct,
    handleFilter,
    getLocationProduct,
    getProductDetail,
    updateAllProduct,
} = products.actions;

export default products.reducer;
