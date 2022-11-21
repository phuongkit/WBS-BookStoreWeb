import { createSlice } from '@reduxjs/toolkit';

export const genres = createSlice({
    name: 'genres',
    initialState: {
        allGenre: {
            data: [],
        },
        oneGenre: {
            data: {},
        },
    },
    reducers: {
        getAllGenres: (state, action) => {
            state.allGenre.data = action.payload;
        },
        getOneGenre: (state, action) => {
            state.oneGenre.data = action.payload;
        },
    },
});
export const { getAllGenres, getOneGenre } = genres.actions;
export default genres.reducer;