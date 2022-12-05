import { createSlice } from '@reduxjs/toolkit';

// const order = localStorage.getItem('order') !== null ? JSON.parse(localStorage.getItem('order')) : {};
export const orders = createSlice({
    name: 'orders',
    initialState: {
        pageOrder: {
            data: [],
        },
        statisticPageOrder: {
            data: {},
        },
        order: {
            //data: [],
            data: {}//order,
        },
    },
    reducers: {
        getPageOrder: (state, action) => {
            state.pageOrder.data = action.payload;
        },
        getStatisticPageOrder: (state, action) => {
            state.statisticPageOrder.data = action.payload || {content: []};
        },
        postOrder: (state, action) => {
            state.pageOrder.data = [...state.pageOrder.data, action.payload];
        },
        updateOrder: (state, action) => {
            state.pageOrder.data = Array.from(state.pageOrder.data || []).map(
                (item) => (item.id === action.payload.id ? action.payload : item)
              );
        },
        deleteOrder: (state, action) => {
            state.pageOrder.data = Array.from(state.pageOrder.data || []).filter(
                (item) => item.id !== action.payload
              );
        },
    },
});
export const { getPageOrder, getStatisticPageOrder, postOrder, updateOrder, deleteOrder } = orders.actions;
export default orders.reducer;
