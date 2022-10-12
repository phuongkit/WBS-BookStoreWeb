import { createSlice } from '@reduxjs/toolkit';

const order = localStorage.getItem('order') !== null ? JSON.parse(localStorage.getItem('order')) : {};
export const orders = createSlice({
    name: 'orders',
    initialState: {
        order: {
            //data: [],
            data: order,
        },
    },
    reducers: {
        postOrder: (state, action) => {
            state.order.data = action.payload;
            const orderData = JSON.stringify(action.payload)
            if(!action.payload.payment.paid) {
                localStorage.setItem("order", orderData)
            }
        },
    },
});
export const { postOrder } = orders.actions;
export default orders.reducer;
