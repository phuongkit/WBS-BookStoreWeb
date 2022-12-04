import React, { createContext, useContext, useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { getProductDetailApi } from '../redux/product/productsApi';

const ProductDetailContext = createContext({});

const ProductDetailProvider = ({ children }) => {
    const dispatch = useDispatch();
    const { productSlug } = useParams();

    // console.log(1);
    const [productDetailData, setProductDetailData] = useState({});

    // const dispatch = useDispatch();
    // const productDetailData = useSelector((state) => state.products.productDetail.data);

    // console.log(productDetailData);
    // console.log(children);

    getProductDetailApi(dispatch, productSlug);

    return (
        <ProductDetailContext.Provider
            value={{
                productDetailData,
            }}
        >
            {children}
        </ProductDetailContext.Provider>
    );
};

const useProductDetail = () => {
    const context = useContext(ProductDetailContext);

    if (context === undefined) {
        throw new Error('useProductDetail must be used within ProductDetailProvider');
    }

    return context;
};

export { ProductDetailProvider, useProductDetail };
