import { createSlice } from '@reduxjs/toolkit';

// const order = localStorage.getItem('order') !== null ? JSON.parse(localStorage.getItem('order')) : {};
export const orders = createSlice({
    name: 'orders',
    initialState: {
        pageOrder: {
            data: [],
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
        postOrder: (state, action) => {
            state.order.data = action.payload;
            // const orderData = JSON.stringify(action.payload)
            // // if(!action.payload?.paid) {
            //     localStorage.setItem("order", orderData)
            // // }
        },
    },
});
export const { getPageOrder, postOrder } = orders.actions;
export default orders.reducer;
