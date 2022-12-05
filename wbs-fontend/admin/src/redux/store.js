import { configureStore } from '@reduxjs/toolkit';

import productModalReducer from './product-modal/productModalSlice';

import cartItemsReducer from './shopping-cart/cartItemsSlide';
import userReducer from './user/userSlice';
import authorReducer from './author/authorsSlice';
import categoryReducer from './category/categoriesSlice';
import commentReducer from './comment/commentsSlice';
import genreReducer from './genre/genresSlice';
import productReducer from './product/productsSlice';
import publisherReducer from './publisher/publishersSlice';
import rateReducer from './rate/ratesSlice';
import supplierReducer from './supplier/suppliersSlice';
import translatorReducer from './translator/tranlatorsSlice';
import searchSlice from './search/searchSlice';
import historyOrdersSlice from './history/historyOrdersSlice';
import orderSlice from './order/orderSlice';
import statisticReducer from './statistic/statisticsSlice';
//khoi tao store
export const store = configureStore({
    reducer: {
        productModal: productModalReducer,
        cartItems: cartItemsReducer,
        users: userReducer,
        authors: authorReducer,
        categories: categoryReducer,
        comments: commentReducer,
        genres: genreReducer,
        products: productReducer,
        publishers: publisherReducer,
        rates: rateReducer,
        search: searchSlice,
        suppliers: supplierReducer,
        translators: translatorReducer,
        historyOrders: historyOrdersSlice,
        orders: orderSlice,
        statistics: statisticReducer,
    },
});
