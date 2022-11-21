import { createSlice } from '@reduxjs/toolkit';

export const suppliers = createSlice({
    name: 'suppliers',
    initialState: {
        allSupplier: {
            data: [],
        },
        oneSupplier: {
            data: {},
        },
    },
    reducers: {
        getAllSuppliers: (state, action) => {
            state.allSupplier.data = action.payload;
        },
        getOneSupplier: (state, action) => {
            state.oneSupplier.data = action.payload;
        },
    },
});
export const { getAllSuppliers, getOneSupplier } = suppliers.actions;
export default suppliers.reducer;