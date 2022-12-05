import { createSlice } from '@reduxjs/toolkit';

export const authors = createSlice({
    name: 'authors',
    initialState: {
        allAuthor: {
            data: [],
        },
        oneAuthor: {
            data: {},
        },
    },
    reducers: {
        getAllAuthors: (state, action) => {
            state.allAuthor.data = action.payload;
        },
        getOneAuthor: (state, action) => {
            state.oneAuthor.data = action.payload;
        },
        createAuthor: (state, action) => {
            state.allAuthor.data.push(action.payload);
        },
    },
});
export const { getAllAuthors, getOneAuthor, createAuthor } = authors.actions;
export default authors.reducer;