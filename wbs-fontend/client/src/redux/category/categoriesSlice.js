import { createSlice } from '@reduxjs/toolkit';

export const categories = createSlice({
    name: 'categories',
    initialState: {
        allCategory: {
            data: [],
        },
        allCategoryHierarchy: {
            data: [],
        },
        oneCategory: {
            data: {},
        },
    },
    reducers: {
        getAllCategories: (state, action) => {
            state.allCategory.data = action.payload;
        },
        getAllCategoriesHierarchy: (state, actions) => {
            state.allCategoryHierarchy = actions.payload;
        },
        getOneCategory: (state, action) => {
            state.oneCategory.data = action.payload;
        },
    },
});
export const { getAllCategories, getAllCategoriesHierarchy, getOneCategory } = categories.actions;
export default categories.reducer;