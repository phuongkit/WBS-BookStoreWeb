import React, { lazy, Suspense } from 'react';
import Loading from '../components/Loading';
import Home from '../pages/User/Home';
// const Home = lazy(() => import('../pages/User/Home'));
export const publishRoutes = [
    {
        index: true,
        element: (
            <Suspense fallback={<Loading />}>
                <Home title="Thegioididong.com - Điện thoại, Laptop, Phụ kiện, Đồng hồ chính hãng" />
            </Suspense>
        ),
    },
];
