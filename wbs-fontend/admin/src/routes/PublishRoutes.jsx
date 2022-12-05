import React, { lazy, Suspense } from 'react';
import Loading from '../components/Loading';
const HomeAdmin = lazy(() => import('../pages/Home/HomeAdmin'));
const Login = lazy(() => import('../pages/Login/Login'));
const ListAuthor = lazy(() => import('../pages/List/ListAuthor'));
const ListCategory = lazy(() => import('../pages/List/ListCategory'));
const ListGenre = lazy(() => import('../pages/List/ListGenre'));
const ListOder = lazy(() => import('../pages/List/ListOder'));
const ListProduct = lazy(() => import('../pages/List/ListProduct'));
const ListPublisher = lazy(() => import('../pages/List/ListPublisher'));
const ListTranslator = lazy(() => import('../pages/List/ListTranslator'));
const ListSupplier = lazy(() => import('../pages/List/ListSupplier'));
const ListUser = lazy(() => import('../pages/List/ListUser'));
const AddProduct = lazy(() => import('../pages/New/NewProduct'));
const AddAuthor = lazy(() => import('../pages/New/NewAuthor'));
export const publishRoutes = [
    {
        index: true,
        element: (
            <Suspense fallback={<Loading />}>
                <HomeAdmin />
            </Suspense>
        ),
    },
    {
        path: 'login',
        element: (
            <Suspense fallback={<Loading />}>
                <Login />
            </Suspense>
        ),
    },
    {
        path: 'products',
        children: [
            {
                index: true,
                element: (
                    <Suspense fallback={<Loading />}>
                        <ListProduct />
                    </Suspense>
                ),
            },
            {
                path: 'addProduct',
                element: (
                    <Suspense fallback={<Loading />}>
                        <AddProduct />
                    </Suspense>
                ),
            },
            {
                path: 'edit/:productId',
                element: (
                    <Suspense fallback={<Loading />}>
                        <AddProduct isUpdate />
                    </Suspense>
                ),
            },
        ],
    },
    {
        path: 'orders',
        element: (
            <Suspense fallback={<Loading />}>
                <ListOder />
            </Suspense>
        ),
    },
    {
        path: 'users',
        element: (
            <Suspense fallback={<Loading />}>
                <ListUser />
            </Suspense>
        ),
    },
    {
        path: 'authors',
        children: [
            {
                index: true,
                element: (
                    <Suspense fallback={<Loading />}>
                        <ListAuthor />
                    </Suspense>
                ),
            }, 
            {
                path: 'addAuthor',
                element: (
                    <Suspense fallback={<Loading />}>
                        <AddAuthor isUpdate />
                    </Suspense>
                ),
            }
        ]
    }, {
        path: 'categories',
        element: (
            <Suspense fallback={<Loading />}>
                <ListCategory />
            </Suspense>
        ),
    }, {
        path: 'genres',
        element: (
            <Suspense fallback={<Loading />}>
                <ListGenre />
            </Suspense>
        ),
    }, {
        path: 'publishers',
        element: (
            <Suspense fallback={<Loading />}>
                <ListPublisher />
            </Suspense>
        ),
    }, {
        path: 'suppliers',
        element: (
            <Suspense fallback={<Loading />}>
                <ListSupplier />
            </Suspense>
        ),
    }, {
        path: 'translators',
        element: (
            <Suspense fallback={<Loading />}>
                <ListTranslator />
            </Suspense>
        ),
    },
];
