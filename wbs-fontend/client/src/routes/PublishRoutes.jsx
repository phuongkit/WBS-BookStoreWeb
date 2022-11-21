import React, { lazy, Suspense } from 'react';
import Loading from '../components/Loading';
const Account = lazy(() => import('../pages/Account'));
const Category = lazy(() => import('../pages/Category'));
const Cart = lazy(() => import('../pages/Cart'));
const Home = lazy(() => import('../pages/Home'));
const Order = lazy(() => import('../pages/Order'));
const ProductDetail = lazy(() => import('../pages/ProductDetail'));
const Search = lazy(() => import('../pages/Search'));
import { ProductDetailProvider } from '../context/ProductDetailContext';
export const publishRoutes = [
    {
        index: true,
        element: (
            <Suspense fallback={<Loading />}>
                <Home title="DealBook - Cửa hàng sách" />
            </Suspense>
        ),
    },
    {
        path: 'cart',
        element: (
            <Suspense fallback={<Loading />}>
                <Cart title="Giỏ hàng - DealBook.com" />
            </Suspense>
        ),
    },
    {
        path: 'order',
        element: (
            <Suspense fallback={<Loading />}>
                <Order title="Đơn hàng - DealBook.com" />
            </Suspense>
        ),
    },
    {
        path: 'account',
        element: (
            <Suspense fallback={<Loading />}>
                <Account title="Tài khoản - DealBook.com" />
            </Suspense>
        ),
    },
    {
        path: ':categorySlug/:productSlug',
        element: (
            <Suspense fallback={<Loading />}>
                <ProductDetailProvider>
                    <ProductDetail />
                </ProductDetailProvider>
            </Suspense>
        ),
    },
    {
        path: 'search',
        // path: 'tim-kiem',
        element: (
            <Suspense fallback={<Loading />}>
                <Search title="Tìm kiếm | Thegioididong.com" />
            </Suspense>
        ),
        children: [
            {
                path: '?keyword=:keyword',
                index: true,
                // path: 'tim-kiem',
                element: (
                    <Suspense fallback={<Loading />}>
                        <Search title="Tìm kiếm | Thegioididong.com" />
                    </Suspense>
                ),
            },
        ],
    },
    {
        path: ':categorySlug',
        element: (
            <Suspense fallback={<Loading />}>
                <Category title="Tìm kiếm | Thegioididong.com" />
            </Suspense>
        ),
    }
];
