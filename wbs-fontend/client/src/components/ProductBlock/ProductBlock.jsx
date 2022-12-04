import React from 'react';
import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Slider from 'react-slick';
import SlideProduct from '../SlideProduct';
import './ProductBlock.scss';
import { getAllProductByOptionApi } from '../../redux/product/productsApi';
import ProductCard from '../ProductCard';
import { EHomeOption } from '../../utils';

function ProductBlock({ homeOption }) {
    const dispatch = useDispatch();
    // const data = homeOption.index === EHomeOption.NEW.index
    //     ? useSelector((state) => state.products.allProduct.new)
    //     : homeOption.index === EHomeOption.SALE.index
    //     ? useSelector((state) => state.products.allProduct.sale)
    //     : homeOption.index === EHomeOption.POPULAR.index
    //     ? useSelector((state) => state.products.allProduct.popular)
    //     : useSelector((state) => state.products.allProduct.data);
    const data1 = useSelector((state) => state.products.allProduct.new);
    const data2 = useSelector((state) => state.products.allProduct.sale);
    const data3 = useSelector((state) => state.products.allProduct.popular);
    const data4 = useSelector((state) => state.products.allProduct.data);
    let data = {content: []};
    if (homeOption.index === EHomeOption.NEW.index) {
       data = data1;
    } else if (homeOption.index === EHomeOption.SALE.index) {
        data = data2;
    } else  if (homeOption.index === EHomeOption.POPULAR.index) {
        data = data3;
    } else {
        data = data4;
    }

    useEffect(() => {
        getAllProductByOptionApi(dispatch, homeOption.index);
    }, []);
    const products = data?.content ? data?.content : [];
    return (
        <section className="_1khoi sachmoi bg-white">
            <div className="container">
                <div className="noidung" style={{ width: '100%' }}>
                    <div className="row">
                        {/* <!--header--> */}
                        <div className="col-12 d-flex justify-content-between align-items-center pb-2 bg-transparent pt-4">
                            <h1 className="header text-uppercase" style={{ fontWeight: '400' }}>
                                {homeOption.name}
                            </h1>
                            <a href={homeOption.slug} className="btn btn-warning btn-sm text-white">
                                Xem tất cả
                            </a>
                        </div>
                    </div>
                    <SlideProduct index={homeOption.index} products={products} />
                </div>
            </div>
        </section>
    );
}

export default ProductBlock;
