import { createSlice } from '@reduxjs/toolkit';

const items = localStorage.getItem('cartItems') !== null ? JSON.parse(localStorage.getItem('cartItems')) : [];

const initialState = {
    value: items,
};

export const cartItemsSlice = createSlice({
    name: 'cartItems',
    initialState,
    reducers: {
        addItem: (state, action) => {
            const newItem = action.payload;
            const oldItem = state.value.filter(
                (e) => e.slug === newItem.slug);
            if (oldItem.length > 0) {
                state.value = state.value.filter(
                    (e) => e.slug !== newItem.slug);
                state.value = [
                    ...state.value,
                    {
                        id: oldItem[0].id,
                        img: newItem.img,
                        name: newItem.name,
                        slug: newItem.slug,
                        availableQuantity: newItem.availableQuantity,
                        soldQuantity: newItem.soldQuantity,
                        originPrice: newItem.originPrice,
                        salePrice: newItem.salePrice,
                        sale: newItem.sale,
                        authors: newItem.authors,
                        star: newItem.star,
                        totalVote: newItem.totalVote,
                        categoryName: newItem.categoryName,
                        categorySlug: newItem.categorySlug,
                        quantity: newItem.quantity + oldItem[0].quantity,
                    },
                ];
            } else {
                state.value = [
                    ...state.value,
                    {
                        id: newItem.id,
                        img: newItem.img,
                        name: newItem.name,
                        slug: newItem.slug,
                        availableQuantity: newItem.availableQuantity,
                        soldQuantity: newItem.soldQuantity,
                        originPrice: newItem.originPrice,
                        salePrice: newItem.salePrice,
                        sale: newItem.sale,
                        authors: newItem.authors,
                        star: newItem.star,
                        totalVote: newItem.totalVote,
                        categoryName: newItem.categoryName,
                        categorySlug: newItem.categorySlug,
                        quantity: newItem.quantity,
                    },
                ];
            }
            localStorage.setItem(
                'cartItems',
                JSON.stringify(state.value.sort((a, b) => (a.id > b.id ? 1 : a.id < b.id ? -1 : 0))),
            );
        },
        updateItem: (state, action) => {
            const newItem = action.payload;
            const oldItem = state.value.filter(
                (e) => e.slug === newItem.slug && e.color === newItem.color && e.size === newItem.size,
            );
            if (oldItem.length > 0) {
                state.value = state.value.filter(
                    (e) => e.slug !== newItem.slug || e.color !== newItem.color || e.size !== newItem.size,
                );

                state.value = [
                    ...state.value,
                    {
                        id: oldItem[0].id,
                        img: newItem.img,
                        name: newItem.name,
                        slug: newItem.slug,
                        availableQuantity: newItem.availableQuantity,
                        soldQuantity: newItem.soldQuantity,
                        originPrice: newItem.originPrice,
                        salePrice: newItem.salePrice,
                        sale: newItem.sale,
                        authors: newItem.authors,
                        star: newItem.star,
                        totalVote: newItem.totalVote,
                        categoryName: newItem.categoryName,
                        categorySlug: newItem.categorySlug,
                        quantity: newItem.quantity,
                    },
                ];
            }
            localStorage.setItem(
                'cartItems',
                JSON.stringify(state.value.sort((a, b) => (a.id > b.id ? 1 : a.id < b.id ? -1 : 0))),
            );
        },
        removeItem: (state, action) => {
            const item = action.payload;
            state.value = state.value.filter(
                (e) => e.id !== item.id,
            );

            localStorage.setItem(
                'cartItems',
                JSON.stringify(state.value.sort((a, b) => (a.id > b.id ? 1 : a.id < b.id ? -1 : 0))),
            );
        },
        clearCart: (state, action) => {
            state.value = [];
            localStorage.removeItem("cartItems")
        }
    },
});

export const { addItem, removeItem, updateItem, clearCart } = cartItemsSlice.actions;

export default cartItemsSlice.reducer;
