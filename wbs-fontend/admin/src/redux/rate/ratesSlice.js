import { createSlice } from '@reduxjs/toolkit';

export const rates = createSlice({
    name: 'rates',
    initialState: {
        rate: {
            data: {},
        },
    },
    reducers: {
        getRate: (state, action) => {
            state.rate.data = action.payload;
        },
        postRate: (state, action) => {
            // state.rate.data = [...state.rate?.data, action.payload];
            state.rate.data = action.payload;
            // state.rate.data = {};
        },
    },
});
// [...state.rate.data, action.payload];
export const { getRate, postRate } = rates.actions;
export default rates.reducer;
