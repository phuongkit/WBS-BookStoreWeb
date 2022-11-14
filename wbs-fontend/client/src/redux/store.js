import { configureStore } from '@reduxjs/toolkit';

import productModalReducer from './product-modal/productModalSlice';

import cartItemsReducer from './shopping-cart/cartItemsSlide';
import userReducer from './user/userSlice';
import categoryReducer from './category/categoriesSlice';
import commentReducer from './comment/commentsSlice';
import productReducer from './product/productsSlice';
import rateReducer from './rate/ratesSlice';
import searchSlice from './search/searchSlice';
import historyOrdersSlice from './history/historyOrdersSlice';
import orderSlice from './order/orderSlice';
//khoi tao store
export const store = configureStore({
    reducer: {
        productModal: productModalReducer,
        cartItems: cartItemsReducer,
        users: userReducer,
        categories: categoryReducer,
        comments: commentReducer,
        products: productReducer,
        rates: rateReducer,
        search: searchSlice,
        historyOrders: historyOrdersSlice,
        orders: orderSlice,
    },
});
