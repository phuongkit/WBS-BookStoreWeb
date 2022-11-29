import { createSlice } from '@reduxjs/toolkit';

export const userSlice = createSlice({
    name: 'users',
    initialState: {
        pageUser: {
            data: {},
        },
        user: {
            data: {},
        },
        auth: {
            currentUser: {},
        },
    },
    reducers: {
        getPageUser: (state, action) => {
            state.pageUser.data = action.payload || { content: [] };
        },
        login: (state, action) => {
            state.auth.currentUser = action.payload;
        },
        logout: (state) => {
            state.auth.currentUser = null;
        },
        updateUser: (state, action) => {
            const newItem = action.payload;
            const oldItem = state.pageUser.data?.content?.filter((e) => e.id === newItem.id);
            if (oldItem.length > 0) {
                state.pageUser.data = {
                    ...state.pageUser.data,
                    content: state.pageUser.data?.content?.filter((e) => e.id !== newItem.id),
                };
                state.pageUser.data = {
                    ...state.pageUser.data,
                    content: [...state.pageUser.data?.content, action.payload],
                };
            } else {
                state.pageUser.data = {
                    ...state.pageUser.data,
                    content: [...state.pageUser.data?.content, action.payload],
                };
            }
        },
    },
});
export const { getPageUser, updateUser, login, logout } = userSlice.actions;

export const selectUser = (state) => state.user.user;

export default userSlice.reducer;
