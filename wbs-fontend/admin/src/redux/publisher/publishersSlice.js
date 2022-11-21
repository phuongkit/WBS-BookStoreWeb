import { createSlice } from '@reduxjs/toolkit';

export const publishers = createSlice({
    name: 'publishers',
    initialState: {
        allPublisher: {
            data: [],
        },
        onePublisher: {
            data: {},
        },
    },
    reducers: {
        getAllPublishers: (state, action) => {
            state.allPublisher.data = action.payload;
        },
        getOnePublisher: (state, action) => {
            state.onePublisher.data = action.payload;
        },
    },
});
export const { getAllPublishers, getOnePublisher } = publishers.actions;
export default publishers.reducer;