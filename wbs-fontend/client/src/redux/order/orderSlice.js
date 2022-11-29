import { createSlice } from '@reduxjs/toolkit';

// const order = localStorage.getItem('order') !== null ? JSON.parse(localStorage.getItem('order')) : {};
export const orders = createSlice({
    name: 'orders',
    initialState: {
        pageOrder: {
            data: {},
        },
        order: {
            //data: [],
            data: {}, //order,
        },
    },
    reducers: {
        getPageOrder: (state, action) => {
            state.pageOrder.data = action.payload;
        },
        postOrder: (state, action) => {
            state.order.data = action.payload;
        },
        updateOrder: (state, action) => {
            state.pageOrder.data = {...state.pageOrder.data, content: Array.from(state.pageOrder?.data?.content || []).map(
                (item) => (item.id === action.payload?.id ? action.payload : item)
              )};
        },
        createOrder: (state, action) => {
            state.order.data = action.payload;
            const orderData = JSON.stringify(action.payload);
            localStorage.setItem('order', orderData);
        },
        deleteOrder: (state, action) => {
            state.order.data = null;
            localStorage.removeItem("order");
        }
    },
});
export const { getPageOrder, postOrder, createOrder,updateOrder, deleteOrder } = orders.actions;
export default orders.reducer;
