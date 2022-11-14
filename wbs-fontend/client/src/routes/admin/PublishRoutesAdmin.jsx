import React, { lazy, Suspense } from 'react';
import Loading from '../../components/Loading';
const HomeAdmin = lazy(() => import('../../pages/Admin/Home/HomeAdmin'));
const Login = lazy(() => import('../../pages/Admin/Login/Login'));
// const ListOders = lazy(() => import('../../pages/Admin/List/ListOder'));
// const ListProducts = lazy(() => import('../../pages/Admin/List/ListProduct'));
// const AddProducts = lazy(() => import('../../pages/Admin/New/NewProduct'));
export const publishRoutesAdmin = [
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
    // {
    //     path: 'products',
    //     children: [
    //         {
    //             index: true,
    //             element: (
    //                 <Suspense fallback={<Loading />}>
    //                     <ListProducts />
    //                 </Suspense>
    //             ),
    //         },
    //         {
    //             path: 'addProduct',
    //             element: (
    //                 <Suspense fallback={<Loading />}>
    //                     <AddProducts />
    //                 </Suspense>
    //             ),
    //         },
    //         {
    //             path: 'edit/:productId',
    //             element: (
    //                 <Suspense fallback={<Loading />}>
    //                     <AddProducts isUpdate />
    //                 </Suspense>
    //             ),
    //         },
    //     ],
    // },
    // {
    //     path: 'orders',
    //     element: (
    //         <Suspense fallback={<Loading />}>
    //             <ListOders />
    //         </Suspense>
    //     ),
    // },
];
