import { createSlice } from '@reduxjs/toolkit';

export const tranlators = createSlice({
    name: 'translators',
    initialState: {
        allTranslator: {
            data: [],
        },
        oneTranslator: {
            data: {},
        },
    },
    reducers: {
        getAllTranslators: (state, action) => {
            state.allTranslator.data = action.payload;
        },
        getOneTranslator: (state, action) => {
            state.oneTranslator.data = action.payload;
        },
    },
});
export const { getAllTranslators, getOneTranslator } = tranlators.actions;
export default tranlators.reducer;