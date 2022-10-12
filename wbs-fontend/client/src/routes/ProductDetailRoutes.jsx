// import { lazy, Suspense, useEffect } from 'react';
// import { useDispatch, useSelector } from 'react-redux';
// const ProductDetail = lazy(() => import('../pages/User/ProductDetail'));
// import Loading from '~/components/Loading';
// import { getAllCategoriesApi } from '../redux/category/categoriesApi';
// import { ProductDetailProvider } from '~/context/ProductDetailContext';

// // const dispatch = useDispatch();

// // const categories = useSelector((state) => state.categories.category.data);


// // useEffect(() => {
// //     getAllCategoriesApi(dispatch);
// // }, []);

// const urls = [
//     ':categorySlug/:productSlug',
//     // 'dienthoai/:productSlug',
//     // 'Phone/:productSlug',
//     // 'laptop/:productSlug',
//     // 'tablet/:productSlug',
//     // 'watch/:productSlug',
//     // 'may-in/:productSlug',
//     // 'muc-in/:productSlug',
//     // 'man-hinh-may-tinh/:productSlug',
//     // 'may-tinh-de-ban/:productSlug',
//     // 'phukien/:productSlug',
//     // 'smartwatch/:productSlug',
// ];

// export const productDetailRoute = {
//     path: ':categorySlug/:productSlug',
//     element: (
//         <Suspense fallback={<Loading />}>
//             <ProductDetailProvider>
//                 <ProductDetail />
//             </ProductDetailProvider>
//         </Suspense>
//     ),
// };


// export const productDetailRoutes = urls.map((url) => ({
//     path: url,
//     element: (
//         <Suspense fallback={<Loading />}>
//             <ProductDetailProvider>
//                 <ProductDetail />
//             </ProductDetailProvider>
//         </Suspense>
//     ),
// }));
