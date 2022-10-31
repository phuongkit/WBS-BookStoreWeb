import React, { lazy, Suspense } from 'react';
import Loading from '@Components/Loading';
const Home = lazy(() => import('../pages/User/Home'));
const ProductDetail = lazy(() => import('../pages/User/ProductDetail'));
import { ProductDetailProvider } from '../context/ProductDetailContext';
export const publishRoutes = [
    {
        index: true,
        element: (
            <Suspense fallback={<Loading />}>
                <Home title="Thegioididong.com - Điện thoại, Laptop, Phụ kiện, Đồng hồ chính hãng" />
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
];
