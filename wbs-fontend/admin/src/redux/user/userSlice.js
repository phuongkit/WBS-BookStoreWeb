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
        }
    },
    reducers: {
        getPageUser: (state, action) => {
            state.pageUser.data = action.payload;
        },
        login: (state, action) => {
            state.auth.currentUser = action.payload;
        },
        logout: (state) => {
            state.auth.currentUser = null;
        },
    },
});
export const { getPageUser, login, logout } = userSlice.actions;

export const selectUser = (state) => state.user.user;

export default userSlice.reducer;