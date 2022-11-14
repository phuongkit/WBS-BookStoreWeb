import { createSlice } from '@reduxjs/toolkit';

export const userSlice = createSlice({
    name: 'users',
    initialState: {
        user: {
            data: {},
        },
        auth: {
            currentUser: {},
        }
    },
    reducers: {
        login: (state, action) => {
            state.auth.currentUser = action.payload;
        },
        logout: (state) => {
            state.auth.currentUser = null;
        },
    },
});
export const { login, logout } = userSlice.actions;

export const selectUser = (state) => state.user.user;

export default userSlice.reducer;
